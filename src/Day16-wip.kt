fun main() {
    fun part1(input: List<String>): Int {
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

        var best = listOf<String>()
        var bestScore = 0

        fun findNext(remaining : Int, current : String, maximum : MutableMap<String, Int>, visited : List<String>, opened: List<String>, cached : MutableMap<String, Int>) {
            if (opened.contains(current)) {
                return
            }

            if (visited.contains(current)) {
                return
            }

            val delta = remaining * rate.getValue(current)

            if (delta > maximum.getOrDefault(current, 0)) {
                maximum[current] = delta
                cached[current] = remaining
            }

            for(next in valves.getValue(current)) {
                findNext(remaining - 1, next, maximum, visited + current, opened, cached)
            }
        }

        fun inspect(remaining: Int, current: String, opened: List<String>, released: Int): Int {
            if (remaining == 0 || zeros.size + opened.size == valves.size) {
                if (released > bestScore) {
                    bestScore = released
                    best = opened
                }
                return released
            }

            val maximum = mutableMapOf<String, Int>()
            val cached = mutableMapOf<String, Int>()
            findNext(remaining, current, maximum, listOf(), opened, cached)

            val nextValve = maximum.maxBy { entry -> entry.value }.key
            val nextRemaining = cached.getValue(nextValve)

            return inspect(nextRemaining, nextValve, opened + current, released + maximum.getValue(nextValve))
        }

        val result = inspect(29, "AA", listOf(), 0)

        return result
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day16_example")
    check(part1(testInputExample) == 1651)
    check(part2(testInputExample) == 0)

    val testInput = readInput("Day16_test")
    println(part1(testInput))
    println(part2(testInput))
}
