fun main() {
    fun score(repeated: Char): Int = when (repeated) {
        in 'a'..'z' -> 1 + (repeated - 'a')
        in 'A'..'Z' -> 27 + (repeated - 'A')
        else -> 0
    }

    fun part1(input: List<String>): Int {
        val result = input.sumOf { line ->
            val half = line.length / 2
            val first = line.substring(0, half)
            val second = line.substring(half, line.length)

            val common = first.toSet().intersect(second.toSet())
            val repeated = common.first()
            score(repeated)
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val result = input
            .windowed(3, 3)
            .sumOf { lines ->
                val common = lines
                    .map(String::toSet)
                    .reduce { a, b -> a.intersect(b) }
                val repeated = common.first()
                score(repeated)
            }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 7691)
    check(part2(testInput) == 2508)
}
