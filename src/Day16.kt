import java.util.regex.Pattern

fun main() {
    fun part1(input: List<String>): Int {
//        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        val data = input.map { line ->
            val valve = line.substringAfter("Valve ").substringBefore(" ")
            val rate = line.substringAfter("rate=").substringBefore(";").toInt()
            val valves = if (line.contains("leads to valve ")) {
                line.substringAfter("leads to valve ").split(',').map { it.trim() }
            } else {
                line.substringAfter("lead to valves ").split(',').map { it.trim() }
            }
            Pair(valve to valves, valve to rate)
        }

        val rate = data.associate { it.second }
        val valves = data.associate { it.first }

        val zeros = rate.filter { it.value == 0 }.map { it.key }

        val maximum = data.associate { it.first.first to 0 }.toMutableMap()

        var best = listOf<String>()
        var bestScore = 0

        fun inspect(remaining: Int, current: String, opened: List<String>, released : Int): Int {
            if (remaining == 0 || zeros.size + opened.size == valves.size) {
                if (released > bestScore) {
                    bestScore = released
                    best = opened
                }
                return released
            }

            if (rate.getValue(current) == 0 || opened.contains(current)) {
                // move
                val max = valves.getValue(current).maxOf { inspect(remaining - 1, it, opened, released) }
                return max
            } else {
                // open
                val delta = remaining * rate.getValue(current)
                val expected = released + delta

                val max = maximum.getValue(current)
                if (expected > max) {
                    maximum.set(current, expected)
                    return inspect(remaining - 1, current, opened + current, expected)
                } else {
                    return 0
                }

            }
        }

        val result = inspect(29, "AA", listOf(), 0)

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day16_example")
    check(part1(testInputExample) == 0)
    check(part2(testInputExample) == 0)

    val testInput = readInput("Day16_test")
    println(part1(testInput))
    println(part2(testInput))
}
