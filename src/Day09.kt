import kotlin.math.max
import kotlin.math.abs

data class Position(val x: Int, val y: Int)

fun main() {

    fun sign(a : Int) : Int =
        if (a > 0) {
            1
        } else if (a < 0) {
            -1
        } else {
            0
        }

    fun snake(commands: List<Pair<Int, Position>>, size : Int): Int {
        val ropes = Array(size) { _ -> Position(0, 0) }

        val positions = mutableSetOf<Position>()

        for (command in commands) {
            val (steps: Int, delta: Position) = command

            for (step in 1..steps) {
                val next = Position(ropes[0].x + delta.x, ropes[0].y + delta.y)

                ropes[0] = next

                for(i in 1 until ropes.size) {
                    val delta = Position(ropes[i - 1].x - ropes[i].x,  ropes[i - 1].y - ropes[i].y)

                    if (max(abs(delta.x), abs(delta.y)) > 1) {
                        val corrected = Position(sign(delta.x), sign(delta.y))
                        ropes[i] = Position(ropes[i].x + corrected.x, ropes[i].y + corrected.y)
                    }
                }

                positions += ropes.last()
            }
        }

        return positions.size
    }

    fun getCommands(input: List<String>) = input.map {
        val (command, steps) = it.split(" ")
        val n = steps.toInt()
        when (command) {
            "U" -> Pair(n, Position(0, 1))
            "D" -> Pair(n, Position(0, -1))
            "L" -> Pair(n, Position(-1, 0))
            "R" -> Pair(n, Position(1, 0))
            else -> throw IllegalStateException()
        }
    }

    fun part1(input: List<String>): Int {
        val commands = getCommands(input)

        return snake(commands, 2)
    }

    fun part2(input: List<String>): Int {
        val commands = getCommands(input)

        return snake(commands, 10)
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day09_example")
    check(part1(testInputExample) == 13)
    check(part2(testInputExample) == 1)

    val testLargerInputExample = readInput("Day09_example_larger")
    check(part2(testLargerInputExample) == 36)

    val testInput = readInput("Day09_test")
    println(part1(testInput))
    println(part2(testInput))
}
