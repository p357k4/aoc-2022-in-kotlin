import kotlin.math.abs

fun main() {
    data class Point(val x: Int, val y: Int)

    fun distance(sx: Int, sy: Int, bx: Int, by: Int): Int = abs(sx - bx) + abs(sy - by)

    fun pairs(input: List<String>) = input.map { line ->
        val sx = line
            .substringBefore(":")
            .substringAfter("x=")
            .substringBefore(", ")
            .toInt()
        val sy = line
            .substringBefore(":")
            .substringAfter(", y=")
            .toInt()

        val bx = line
            .substringAfter("closest beacon is at x=")
            .substringBefore(", ")
            .toInt()
        val by = line
            .substringAfter("closest beacon is at x=")
            .substringAfter(", y=")
            .toInt()

        Pair(Point(sx, sy), Point(bx, by))
    }

    fun part1(input: List<String>, y: Int): Int {
        val data = pairs(input)

        val minimum = data.associate { p -> p.first to distance(p.first.x, p.first.y, p.second.x, p.second.y) }

        val minX = data.minOf { p -> p.first.x - minimum.getValue(p.first) }
        val maxX = data.maxOf { p -> p.first.x + minimum.getValue(p.first) }

        val result = (minX..maxX).count { x ->
            data.any { p ->
                val actual = distance(p.first.x, p.first.y, x, y)
                actual <= minimum.getValue(p.first) && !(x == p.second.x && y == p.second.y)
            }
        }

        return result
    }

    fun part2(input: List<String>, maxX: Int, maxY: Int): Long {
        val data = pairs(input)

        val minimum = data.associate { p -> p.first to distance(p.first.x, p.first.y, p.second.x, p.second.y) }

        fun covered(x: Int, y: Int): Boolean {
            return x !in 0..maxX || y !in 0..maxY || data.any { p ->
                val actual = distance(p.first.x, p.first.y, x, y)
                actual <= minimum.getValue(p.first)
            }
        }

        for (sb in data) {
            val r = minimum.getValue(sb.first)
            for (r0 in 0..r + 1) {
                val x0 = sb.first.x + r0
                val x1 = sb.first.x - r0
                val y0 = sb.first.y + (r + 1 - r0)
                val y1 = sb.first.y - (r + 1 - r0)

                if (!covered(x0, y0)) {
                    return x0 * 4_000_000L + y0
                }
                if (!covered(x0, y1)) {
                    return x0 * 4_000_000L + y1
                }
                if (!covered(x1, y0)) {
                    return x1 * 4_000_000L + y0
                }
                if (!covered(x1, y1)) {
                    return x1 * 4_000_000L + y1
                }
            }
        }

        throw IllegalStateException("distress beacon not found")
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day15_example")
    check(part1(testInputExample, 10) == 26)
    check(part2(testInputExample, 20, 20) == 56000011L)

    val testInput = readInput("Day15_test")
    println(part1(testInput, 2_000_000))
    println(part2(testInput, 4_000_000, 4_000_000))
}
