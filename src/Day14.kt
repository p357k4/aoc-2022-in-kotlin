import java.util.regex.Pattern

fun main() {
    //    498,4 -> 498,6 -> 496,6
//    503,4 -> 502,4 -> 502,9 -> 494,9
    data class Point(val x: Int, val y: Int)

    fun sign(a: Int): Int =
        if (a > 0) {
            1
        } else if (a < 0) {
            -1
        } else {
            0
        }

    fun simulation(map: Array<CharArray>, bottom: Int): Int {
        var points = 0
        while (true) {
            var x = 500
            var y = 0
            while (true) {
                if (y > bottom || map[y][x] != Char(0)) {
                    return points
                }

                if (map[y + 1][x] == Char(0)) {
                    y += 1
                    continue
                }

                if (map[y + 1][x - 1] == Char(0)) {
                    x -= 1
                    y += 1
                    continue
                }

                if (map[y + 1][x + 1] == Char(0)) {
                    x += 1
                    y += 1
                    continue
                }

                map[y][x] = 'o'
                points += 1
                break
            }
        }
    }

    fun part1(input: String): Int {
        val lines = input.lines()
            .map { line ->
                line.split(Pattern.compile(" -> "))
                    .map {
                        val (x, y) = it.split(",")
                        Point(x.toInt(), y.toInt())
                    }
            }

        val map = Array(1000) { CharArray(1000) }

        val bottom = lines.flatten().maxOf { it.y }

        for (line in lines) {
            for (i in 1 until line.size) {
                val dx = sign(line[i].x - line[i - 1].x)
                val dy = sign(line[i].y - line[i - 1].y)

                var x = line[i - 1].x
                var y = line[i - 1].y

                do {
                    map[y][x] = '#'
                    x += dx
                    y += dy
                } while (!(line[i].x == x && line[i].y == y))
                map[y][x] = '#'
            }
        }

        val result = simulation(map, bottom)

        return result
    }

    fun part2(input: String): Int {
        val lines = input.lines()
            .map { line ->
                line.split(Pattern.compile(" -> "))
                    .map {
                        val (x, y) = it.split(",")
                        Point(x.toInt(), y.toInt())
                    }
            }

        val map = Array(1000) { CharArray(1000) }

        val bottom = lines.flatten().maxOf { it.y }

        for (line in lines + listOf(listOf(Point(0, bottom + 2), Point(999, bottom+2)))) {
            for (i in 1 until line.size) {
                val dx = sign(line[i].x - line[i - 1].x)
                val dy = sign(line[i].y - line[i - 1].y)

                var x = line[i - 1].x
                var y = line[i - 1].y

                do {
                    map[y][x] = '#'
                    x += dx
                    y += dy
                } while (!(line[i].x == x && line[i].y == y))
                map[y][x] = '#'
            }
        }

        val result = simulation(map, Int.MAX_VALUE)

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readText("Day14_example")
    check(part1(testInputExample) == 24)
    check(part2(testInputExample) == 93)

    val testInput = readText("Day14_test")
    println(part1(testInput))
    println(part2(testInput))
}
