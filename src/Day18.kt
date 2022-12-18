fun main() {
    data class Cube(val x: Int, val y: Int, val z: Int)

    fun part1(input: List<String>): Int {
        val cubes = input.map {
            val split = it.split(',').map { it.toInt() }
            Cube(split[0], split[1], split[2])
        }

        var walls = 0
        for (cube in cubes) {
            walls += 6
            for (other in cubes) {
                if (cube == other) {
                    continue
                }

                if (cube.copy(x = cube.x + 1) == other) {
                    walls -= 1
                    continue
                }

                if (cube.copy(x = cube.x - 1) == other) {
                    walls -= 1
                    continue
                }

                if (cube.copy(y = cube.y + 1) == other) {
                    walls -= 1
                    continue
                }

                if (cube.copy(y = cube.y - 1) == other) {
                    walls -= 1
                    continue
                }

                if (cube.copy(z = cube.z + 1) == other) {
                    walls -= 1
                    continue
                }

                if (cube.copy(z = cube.z - 1) == other) {
                    walls -= 1
                    continue
                }
            }
        }

        return walls
    }

    fun part2(input: List<String>): Int {
        val magma = input.map { line ->
            val split = line.split(',').map(String::toInt)
            Cube(split[0], split[1], split[2])
        }

        val minX = magma.minOf { it.x } - 1
        val maxX = magma.maxOf { it.x } + 1
        val minY = magma.minOf { it.y } - 1
        val maxY = magma.maxOf { it.y } + 1
        val minZ = magma.minOf { it.z } - 1
        val maxZ = magma.maxOf { it.z } + 1

        val water = mutableListOf<Cube>()

        fun flood(drop: Cube): Int {
            if (drop.x !in minX..maxX || drop.y !in minY..maxY || drop.z !in minZ..maxZ) {
                return 0
            }

            if (drop in water) {
                return 0
            }

            if (drop in magma) {
                return 1
            }

            water.add(drop)

            return flood(drop.copy(x = drop.x + 1)) +
                    flood(drop.copy(x = drop.x - 1)) +
                    flood(drop.copy(y = drop.y + 1)) +
                    flood(drop.copy(y = drop.y - 1)) +
                    flood(drop.copy(z = drop.z + 1)) +
                    flood(drop.copy(z = drop.z - 1))
        }

        val result = flood(Cube(maxX, maxY, maxZ))
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day18_example")
    check(part1(testInputExample) == 64)
    check(part2(testInputExample) == 58)

    val testInput = readInput("Day18_test")
    println(part1(testInput))
    println(part2(testInput))
}
