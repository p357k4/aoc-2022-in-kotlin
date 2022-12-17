fun main() {
    fun part1(input: String): Int {
        val shapes = arrayOf(
            arrayOf(
                "####"
            ),

            arrayOf(
                ".#.",
                "###",
                ".#."
            ),

            arrayOf(
                "..#",
                "..#",
                "###"
            ),

            arrayOf(
                "#",
                "#",
                "#",
                "#"
            ),

            arrayOf(
                "##",
                "##",
            )
        )

        val tunnel = mutableListOf(
            "+-------+".toCharArray()
        )

        var nextShape = true
        var shape = shapes[0]
        var shapeColumn = 3
        var shapeRow = 0
        var jetCounter = 0
        var shapeCounter = 0

        while (shapeCounter < 2022) {
            fun nonEmpty(line: CharArray): Boolean {
                for (i in 1..line.size - 2) {
                    if (line[i] != '.') {
                        return true
                    }
                }

                return false
            }

            fun blocked(row: Int, column: Int): Boolean {
                for (i in shape.indices) {
                    for (j in shape[i].indices) {
                        if (shape[i][j] != '.' && tunnel[row + i][column + j] != '.') {
                            return true
                        }
                    }
                }

                return false
            }

            fun add(row: Int, column: Int): Boolean {
                for (i in shape.indices) {
                    for (j in shape[i].indices) {
                        val c = shape[i][j]
                        tunnel[row + i][column + j] = c
                    }
                }

                return false
            }

            if (nextShape) {
                nextShape = false
                shapeColumn = 3
                shapeRow = 0
                shape = shapes[shapeCounter % shapes.size]

                val toAdd = 4 + shape.size
                val emptiness = List(toAdd) { "|.......|".toCharArray() }
                tunnel.addAll(0, emptiness)
            }

            if (!blocked(shapeRow + 1, shapeColumn)) {
                if (nonEmpty(tunnel.first())) {
                    shapeRow += 1
                } else {
                    tunnel.removeAt(0)
                }

                val jet = when {
                    input[jetCounter] == '<' -> {
                        -1
                    }

                    input[jetCounter] == '>' -> {
                        1
                    }

                    else -> {
                        throw IllegalStateException("jet error")
                    }
                }

                if (!blocked(shapeRow, shapeColumn + jet)) {
                    shapeColumn += jet
                }

                jetCounter = (jetCounter + 1) % input.length
            } else {
                add(shapeRow, shapeColumn)
                nextShape = true
                shapeCounter += 1
            }
        }

        return tunnel.size - 1
    }

    fun part2(input: String): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readText("Day17_example")
    check(part1(testInputExample) == 3068)
    check(part2(testInputExample) == 0)

    val testInput = readText("Day17_test")
    println(part1(testInput))
    println(part2(testInput))
}
