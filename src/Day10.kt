sealed interface Command {
    object MicroNoop : Command
    data class MicroAddX(val v: Int) : Command
}

fun main() {
    fun commands(input: List<String>) = input
        .flatMap { line ->
            val split = line.split(' ')
            when (split[0]) {
                "noop" -> sequenceOf(Command.MicroNoop)
                "addx" -> sequenceOf(Command.MicroNoop, Command.MicroAddX(split[1].toInt()))
                else -> throw IllegalStateException()
            }
        }

    fun part1(input: List<String>): Int {
        val result = commands(input)
            .foldIndexed(Pair(1, 0)) { idx, acc, command ->
                val cycle = idx + 1
                val strength = cycle * if ((cycle - 20) % 40 == 0) acc.first else 0
                val delta = when (command) {
                    is Command.MicroAddX -> command.v
                    is Command.MicroNoop -> 0
                }
                Pair(acc.first + delta, acc.second + strength)
            }

        return result.second
    }

    fun part2(input: List<String>): String {
        val result = commands(input)
            .foldIndexed(Pair(0, "")) { idx, acc, command ->
                val pixel = idx % 40
                val v = acc.first

                val c = if (v <= pixel && pixel <= v + 2) "#" else "."

                val delta = when (command) {
                    is Command.MicroAddX -> command.v
                    is Command.MicroNoop -> 0
                }

                Pair(acc.first + delta, acc.second + c)
            }

        return result.second.chunked(40).joinToString("\n")
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day10_example")
    check(part1(testInputExample) == 13140)
    println(part2(testInputExample))

    val testInput = readInput("Day10_test")
    println(part1(testInput))
    println(part2(testInput))
}
