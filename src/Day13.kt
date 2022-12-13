import kotlin.math.min

sealed interface Message {
    data class Number(val value: Int) : Message
    data class Messages(val messages: MutableList<Message>, val parent: Messages?) : Message
}

enum class Ordered {
    YES, NO, MAYBE
}


fun main() {
    fun decode(input: String): Message {
        val parent = Message.Messages(mutableListOf(), null)
        var current: Message.Messages? = parent
        var index = 0
        while (true) {
            if (index == input.length) {
                break
            }

            if (input[index] == '[') {
                val child = Message.Messages(mutableListOf(), current)
                current?.messages?.add(child)
                current = child
                index++
                continue;
            }

            if (input[index] == ']') {
                current = current?.parent
                index++
                continue
            }

            if (input[index] == ',') {
                index++
                continue
            }

            var number = 0
            while (input[index].isDigit()) {
                number = 10 * number + input[index].digitToInt()
                index++
            }

            current?.messages?.add(Message.Number(number))
        }

        return parent
    }

    fun isOrdered(left: Message, right: Message): Ordered {
        if (left is Message.Number && right is Message.Number) {
            if (left.value < right.value) {
                return Ordered.YES
            }

            if (left.value > right.value) {
                return Ordered.NO
            }

            return Ordered.MAYBE
        }

        if (left is Message.Messages && right is Message.Number) {
            return isOrdered(left, Message.Messages(mutableListOf(right), null))
        }

        if (left is Message.Number && right is Message.Messages) {
            return isOrdered(Message.Messages(mutableListOf(left), null), right)
        }

        if (left is Message.Messages && right is Message.Messages) {
            for(i in 0 until left.messages.size.coerceAtMost(right.messages.size)) {
                val result = isOrdered(left.messages[i], right.messages[i])
                if (result != Ordered.MAYBE) {
                    return result
                }
            }

            if (left.messages.size < right.messages.size) {
                return Ordered.YES
            }

            if (left.messages.size > right.messages.size) {
                return Ordered.NO
            }

            return Ordered.MAYBE
        }

        throw IllegalStateException("cannot end with MAYBE order")
    }

    fun part1(input: String): Int {
        val result =
            input
                .split("\n\n")
                .mapIndexed() { index, packet ->
                    val (left, right) = packet.lines().map { decode(it) }

                    val result = isOrdered(left, right)
                    if (result == Ordered.YES) {
                        index + 1
                    } else {
                        0
                    }
                }.sum()

        return result
    }

    fun part2(input: String): Int {
        val marker2 = decode("[[2]]")
        val marker6 = decode("[[6]]")

        val result =
            (input.lines().filterNot { it.isEmpty() }.map { line -> decode(line) } + marker2 + marker6)
                .sortedWith { a, b ->
                    if (isOrdered(a, b) == Ordered.NO) {
                        1
                    } else {
                        -1
                    }
                }

        val divider2 = result.indexOf(marker2) + 1
        val divider6 = result.indexOf(marker6) + 1

        return divider2 * divider6
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readText("Day13_example")
    check(part1(testInputExample) == 13)
    check(part2(testInputExample) == 140)

    val testInput = readText("Day13_test")
    println(part1(testInput))
    println(part2(testInput))
}
