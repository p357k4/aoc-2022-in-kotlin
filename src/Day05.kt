fun main() {
    fun part1(input: String): String {
        val (header, instruction) = input.split("\n\n")

        val crates = arrayOf(mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>())

        for(line in header.lines()) {
            line.windowed(3, 4).forEachIndexed { idx, data ->
                val c = data[1]
                if (c in 'A' .. 'Z') {
                    crates[idx].add(c)
                }
            }
        }

        for(instruction in instruction.lines()) {
            val split = instruction.split(" ")

            //val command = split[0]
            val n = split[1].toInt()
            //val from = split[2]
            val source = split[3].toInt() - 1
            //val to = split[4]
            val destination = split[5].toInt() - 1

            val cargo = crates[source].subList(0, n)
            cargo.reverse()
            crates[destination].addAll(0, cargo)
            crates[source] = crates[source].subList(n, crates[source].size)
        }

        val result = crates.filter {!it.isEmpty() }.map { it.first() }.joinToString("")

        return result
    }

    fun part2(input: String): String {
        val (header, instruction) = input.split("\n\n")

        val crates = arrayOf(mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>(), mutableListOf<Char>())

        for(line in header.lines()) {
            line.windowed(3, 4).forEachIndexed { idx, data ->
                val c = data[1]
                if (c in 'A' .. 'Z') {
                    crates[idx].add(c)
                }
            }
        }

        for(instruction in instruction.lines()) {
            val split = instruction.split(" ")

            //val command = split[0]
            val n = split[1].toInt()
            //val from = split[2]
            val source = split[3].toInt() - 1
            //val to = split[4]
            val destination = split[5].toInt() - 1

            val cargo = crates[source].subList(0, n)
            crates[destination].addAll(0, cargo)
            crates[source] = crates[source].subList(n, crates[source].size)
        }

        val result = crates.filter {!it.isEmpty() }.map { it.first() }.joinToString("")

        return result
    }

    check(part1(readText("Day05_example")) == "CMZ")
    check(part2(readText("Day05_example")) == "MCD")

    // test if implementation meets criteria from the description, like:
    val testInput = readText("Day05_test")
    check(part1(testInput) == "NTWZZWHFV")
    check(part2(testInput) == "BRZGFVBTJ")
}
