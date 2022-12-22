fun main() {
    fun part1(input: String): Long {
        val data = input.split("\n\n")

        val path = data[1]

        val lines = data[0].lines()
        val rows = lines.size
        val columns = lines.maxOf { it.length }

        val map = lines.map { it.toCharArray() + CharArray(columns - it.length) { ' ' } }.toTypedArray()

        var row = 0
        var column = map[row].indexOf('.')
        var dirColumn = 1
        var dirRow = 0
        var steps = 0
        var angle = 0

        fun go() {
            var nextColumn = column
            var nextRow = row

            while (steps > 0) {
                nextColumn = (nextColumn + dirColumn).mod(columns)
                nextRow = (nextRow + dirRow).mod(rows)

                val next = map[nextRow][nextColumn]

                if (next == ' ') {
                    continue
                }

                if (next == '#') {
                    break
                }

                column = nextColumn
                row = nextRow

                steps--
            }

            steps = 0
        }

        for (p in path) {
            var prevDirColumn = dirColumn
            var prevDirRow = dirRow

            when (p) {
                'R' -> {
                    go()
                    angle = (angle + 1).mod(4)
                    dirColumn = -prevDirRow
                    dirRow = prevDirColumn
                }

                'L' -> {
                    go()
                    angle = (angle - 1).mod(4)
                    dirColumn = prevDirRow
                    dirRow = -prevDirColumn
                }

                in '0'..'9' -> {
                    steps = 10 * steps + (p - '0')
                }
            }
        }
        go()

        return 1000L*(row + 1) + 4 * (column + 1) + angle
    }

    fun part2(input: String): Long {
        return 0L
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readText("Day22_example")
    check(part1(testInputExample) == 6032L)
    check(part2(testInputExample) == 0L)

    val testInput = readText("Day22_test")
    println(part1(testInput))
    println(part2(testInput))
}
