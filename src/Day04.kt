fun main() {
    data class Range(val a: Int, val b: Int)

    fun String.build(): Range {
        val ab = this.split('-').map { it.toInt() }
        return Range(ab[0], ab[1])
    }

    fun part1(input: List<String>): Int {
        val result = input
            .map { line -> line.split(',') }
            .count { split ->
                val (left, right) = split.map { it.build() }
                (left.a <= right.a && right.b <= left.b) || (right.a <= left.a && left.b <= right.b)
            }
        return result
    }

    fun part2(input: List<String>): Int {
        val result = input
            .map { line -> line.split(',') }
            .count { split ->
                val (left, right) = split.map { it.build() }
                (left.a <= right.a && right.a <= left.b) || (left.a <= right.b && right.b <= left.b) ||
                        (right.a <= left.a && left.a <= right.b) || (right.a <= left.b && left.b <= right.b)
            }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 576)
    check(part2(testInput) == 905)
}
