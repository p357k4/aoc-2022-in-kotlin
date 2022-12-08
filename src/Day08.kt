fun main() {

    fun part1(input: List<String>): Int {
        val trees = input.map { it.map { it.digitToInt() }.toIntArray() }.toTypedArray()
        val visible = Array(input.size) {BooleanArray(input.size)}

        for(i in 0 until input.size) {
            var max = -1
            for (j in 0 .. input.size - 1) {
                if (trees[i][j] > max) {
                    max = trees[i][j]
                    visible[i][j] = true
                }
            }

            max = -1
            for(j in input.size - 1 downTo 0) {
                if (trees[i][j] > max) {
                    max = trees[i][j]
                    visible[i][j] = true
                }
            }
        }

        for(j in 0 until input.size) {
            var max = -1
            for (i in 0 .. input.size - 1) {
                if (trees[i][j] > max) {
                    max = trees[i][j]
                    visible[i][j] = true
                }
            }

            max = -1
            for(i in input.size - 1 downTo 0) {
                if (trees[i][j] > max) {
                    max = trees[i][j]
                    visible[i][j] = true
                }
            }
        }

        var counter = 0
        for(i in 0 .. visible.size - 1) {
            for(j in 0 .. visible[i].size - 1) {
                if (visible[i][j]) {
                    counter++
                }
            }
        }

        return counter
    }

    fun part2(input: List<String>): Int {
        val trees = input.map { it.map { it.digitToInt() }.toIntArray() }.toTypedArray()

        fun analyze(i0 : Int, j0 : Int) : Int {
            var scenic = 1
            var counter = 1
            for(j in j0 + 1 .. trees[i0].size - 2) {
                if (trees[i0][j0] <= trees[i0][j]) {
                    break;
                }
                counter++
            }
            scenic *= counter

            counter = 1
            for(j in j0 - 1 downTo  1) {
                if (trees[i0][j0] <= trees[i0][j]) {
                    break;
                }
                counter++
            }
            scenic *= counter

            counter = 1
            for(i in i0 + 1 .. trees.size - 2) {
                if (trees[i0][j0] <= trees[i][j0]) {
                    break;
                }
                counter++
            }
            scenic *= counter

            counter = 1
            for(i in i0 - 1 downTo  1) {
                if (trees[i0][j0] <= trees[i][j0]) {
                    break;
                }
                counter++
            }
            scenic *= counter

            return scenic;
        }

        var max = 0

        for(i in 1 .. trees.size - 2) {
            for(j in 1 .. trees[i].size - 2) {
                val scenic = analyze(i, j)
                if (scenic > max) {
                    max = scenic
                }
            }
        }

        return max
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day08_example")
    check(part1(testInputExample) == 21)
    check(part2(testInputExample) == 8)

    val testInput = readInput("Day08_test")
    println(part1(testInput))
    println(part2(testInput))
}
