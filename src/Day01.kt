fun main() {
    fun part1(input: List<String>): Int {
        val energy = input
            .map(String::trim)
            .fold(Pair(listOf<Int>(), 0)) { acc, t ->
                if (t.trim().isEmpty()) {
                    Pair(acc.first + acc.second, 0)
                } else {
                    Pair(acc.first, acc.second + t.toInt())
                }
            }

        return energy.first.max()
    }

    fun part2(input: List<String>): Int {
        val energy = input
            .map(String::trim)
            .fold(Pair(listOf<Int>(), 0)) { acc, t ->
                if (t.trim().isEmpty()) {
                    Pair(acc.first + acc.second, 0)
                } else {
                    Pair(acc.first, acc.second + t.toInt())
                }
            }

        return energy.first.sorted().takeLast(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 72478)

    val input = readInput("Day01_test")
    check(part2(testInput) == 210367)
}
