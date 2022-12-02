

fun main() {
    val loss = 0
    val draw = 3
    val win = 6

    val rock = 1
    val paper = 2
    val scissors = 3

    // A for Rock, B for Paper, and C for Scissors
    // X for Rock, Y for Paper, and Z for Scissors
    fun part1(input: List<String>): Int {
        val score = input
            .map { line ->
                val split = line.split(' ')
                Pair(split[0][0], split[1][0])
            }
            .sumOf { pair ->
                val result = when (pair) {
                    Pair('A', 'X') -> Pair(draw, rock)
                    Pair('B', 'Y') -> Pair(draw, paper)
                    Pair('C', 'Z') -> Pair(draw, scissors)
                    Pair('C', 'X') -> Pair(win, rock)
                    Pair('A', 'Y') -> Pair(win, paper)
                    Pair('B', 'Z') -> Pair(win, scissors)
                    Pair('B', 'X') -> Pair(loss, rock)
                    Pair('C', 'Y') -> Pair(loss, paper)
                    Pair('A', 'Z') -> Pair(loss, scissors)
                    else -> Pair(0, 0)
                }
                result.first + result.second
            }

        return score
    }

    // A for Rock, B for Paper, and C for Scissors
    // X for loose, Y for draw, and Z for win
    fun part2(input: List<String>): Int {
        val score = input
            .map { line ->
                val split = line.split(' ')
                Pair(split[0][0], split[1][0])
            }
            .sumOf { pair ->
                val result = when (pair) {
                    Pair('A', 'X') -> Pair(loss, scissors)
                    Pair('A', 'Y') -> Pair(draw, rock)
                    Pair('A', 'Z') -> Pair(win, paper)
                    Pair('B', 'X') -> Pair(loss, rock)
                    Pair('B', 'Y') -> Pair(draw, paper)
                    Pair('B', 'Z') -> Pair(win, scissors)
                    Pair('C', 'X') -> Pair(loss, paper)
                    Pair('C', 'Y') -> Pair(draw, scissors)
                    Pair('C', 'Z') -> Pair(win, rock)
                    else -> Pair(0, 0)
                }
                result.first + result.second
            }

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 12156)
    check(part2(testInput) == 10835)
}
