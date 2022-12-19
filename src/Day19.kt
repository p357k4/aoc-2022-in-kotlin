fun main() {
    data class Cost(val ore: Int, val clay: Int, val obsidian: Int)
    data class Blueprint(val ore: Cost, val clay: Cost, val obsidian: Cost, val geode: Cost)

    fun decode(input: List<String>) = input.map { line ->
        val segments = line.substringAfter(":").split(".")
        val oreOre = segments[0].substringAfter("ore robot costs ").substringBefore(" ore").toInt()
        val clayOre = segments[1].substringAfter("clay robot costs ").substringBefore(" ore").toInt()
        val obsidianOre = segments[2].substringAfter("obsidian robot costs ").substringBefore(" ore").toInt()
        val obsidianClay = segments[2].substringAfter("ore and ").substringBefore(" clay").toInt()
        val geodeOre = segments[3].substringAfter("geode robot costs ").substringBefore(" ore").toInt()
        val geodeObsidian = segments[3].substringAfter("ore and ").substringBefore(" obsidian").toInt()

        Blueprint(
            Cost(oreOre, 0, 0),
            Cost(clayOre, 0, 0),
            Cost(obsidianOre, obsidianClay, 0),
            Cost(geodeOre, 0, geodeObsidian)
        )
    }

    data class Robots(val ore: Int, val clay: Int, val obsidian: Int, val geode: Int)
    data class Resources(val ore: Int, val clay: Int, val obsidian: Int, val geode: Int)
    data class State(val resources: Resources, val robots: Robots)

    fun enough(resources: Resources, cost: Cost): Boolean =
        resources.ore >= cost.ore && resources.clay >= cost.clay && resources.obsidian >= cost.obsidian

    fun part1(input: List<String>): Int {
        val blueprints = decode(input)

        for (blueprint in blueprints) {
            var state = State(Resources(0, 0, 0, 0), Robots(1, 0, 0, 0))

            for (i in 1..24) {
                var nextRobots = state.robots
                var nextResources = state.resources

                if (enough(state.resources, blueprint.ore)) {
                    nextResources = nextResources.copy(
                        nextResources.ore - blueprint.ore.ore,
                        nextResources.clay - blueprint.ore.clay,
                        nextResources.obsidian - blueprint.ore.obsidian,
                        0
                    )
                    nextRobots = nextRobots.copy(ore = nextRobots.ore + 1)
                }

                if (enough(state.resources, blueprint.clay)) {
                    nextResources = nextResources.copy(
                        nextResources.ore - blueprint.clay.ore,
                        nextResources.clay - blueprint.clay.clay,
                        nextResources.obsidian - blueprint.clay.obsidian,
                        0
                    )
                    nextRobots = nextRobots.copy(clay = nextRobots.clay + 1)
                }

                if (enough(state.resources, blueprint.obsidian)) {
                    nextResources = nextResources.copy(
                        nextResources.ore - blueprint.obsidian.ore,
                        nextResources.clay - blueprint.obsidian.clay,
                        nextResources.obsidian - blueprint.obsidian.obsidian,
                        0
                    )
                    nextRobots = nextRobots.copy(obsidian = nextRobots.obsidian + 1)
                }

                if (enough(state.resources, blueprint.geode)) {
                    nextResources = nextResources.copy(
                        nextResources.ore - blueprint.geode.ore,
                        nextResources.clay - blueprint.geode.clay,
                        nextResources.obsidian - blueprint.geode.obsidian,
                        0
                    )
                    nextRobots = nextRobots.copy(geode = nextRobots.geode + 1)
                }

                nextResources = nextResources.copy(
                    nextResources.ore + state.robots.ore,
                    nextResources.clay + state.robots.clay,
                    nextResources.obsidian + state.robots.obsidian,
                    nextResources.geode + state.robots.geode,
                )

                state = State(nextResources, nextRobots)
            }

            println(state)
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day19_example")
    check(part1(testInputExample) == 64)
    check(part2(testInputExample) == 58)

    val testInput = readInput("Day19_test")
    println(part1(testInput))
    println(part2(testInput))
}
