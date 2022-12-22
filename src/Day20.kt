fun main() {
    fun part1(input: List<String>): Int {
        val original = input.mapIndexed { idx, element -> Pair(idx, element.toInt()) }
        val numbers = original.toMutableList()
        val modulo = numbers.size

        for (number in original.indices) {
            val idx = numbers.indexOfFirst { it.first == number }
            val next = (idx + numbers[idx].second).mod(modulo - 1)

            numbers.add(next, numbers.removeAt(idx))
        }

        val zero = numbers.indexOfFirst { it.second == 0 }
        val result =
            numbers[(zero + 1000).mod(modulo)].second + numbers[(zero + 2000).mod(modulo)].second + numbers[(zero + 3000).mod(
                modulo
            )].second

        return result
    }

    fun part2(input: List<String>): Long {
        val key = 811589153L

        val original = input.mapIndexed { idx, element -> Pair(idx, key * element.toLong()) }
        val numbers = original.toMutableList()
        val modulo = numbers.size

        for (i in 1..10) {
            for (number in original.indices) {
                val idx = numbers.indexOfFirst { it.first == number }
                val next = (idx + numbers[idx].second).mod(modulo - 1)

                numbers.add(next, numbers.removeAt(idx))
            }
        }

        val zero = numbers.indexOfFirst { it.second == 0L }
        val result =
            numbers[(zero + 1000).mod(modulo)].second + numbers[(zero + 2000).mod(modulo)].second + numbers[(zero + 3000).mod(
                modulo
            )].second

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day20_example")
    check(part1(testInputExample) == 3)
    check(part2(testInputExample) == 1623178306L)

    val testInput = readInput("Day20_test")
    println(part1(testInput))
    println(part2(testInput))
}
