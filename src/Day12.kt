fun main() {
    data class Node(val i: Int, val j: Int, val char: Char, var cost: Int) {
        val height = when (char) {
            'S' -> 'a'
            'E' -> 'z'
            else -> char
        }.code
    }

    fun exists(i: Int, j: Int, map: Array<Node>): Boolean {
        return map.any { it.i == i && it.j == j }
    }

    fun node(i: Int, j: Int, map: Array<Node>): Node {
        return map.first { it.i == i && it.j == j }
    }

    fun dijkstra(parent: Node, node: Node, map: Array<Node>) {
        if (node.height - parent.height > 1) {
            return
        }

        val cost = parent.cost + 1
        if (cost >= node.cost) {
            return
        }

        node.cost = cost

        if (exists(node.i - 1, node.j, map)) {
            dijkstra(node, node(node.i - 1, node.j, map), map)
        }

        if (exists(node.i + 1, node.j, map)) {
            dijkstra(node, node(node.i + 1, node.j, map), map)
        }

        if (exists(node.i, node.j - 1, map)) {
            dijkstra(node, node(node.i, node.j - 1, map), map)
        }

        if (exists(node.i, node.j + 1, map)) {
            dijkstra(node, node(node.i, node.j + 1, map), map)
        }
    }

    fun analyse(input: List<String>) = input
        .mapIndexed { row, line ->
            line
                .toCharArray()
                .mapIndexed { column, char -> Node(row, column, char, Int.MAX_VALUE) }
        }
        .flatten()
        .toTypedArray()

    fun part1(input: List<String>): Int {
        val map = analyse(input)

        map
            .filter { it.char == 'S' }
            .forEach { node ->
                dijkstra(Node(-1, -1, 'S', -1), node, map)
            }

        val end = map.first { it.char == 'E' }

        return end.cost
    }

    fun part2(input: List<String>): Int {
        val map = analyse(input)

        map
            .filter { it.char == 'a' || it.char == 'S' }
            .forEach { node -> dijkstra(Node(-1, -1, 'S', -1), node, map) }

        val end = map.first { it.char == 'E' }
        return end.cost
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day12_example")
    check(part1(testInputExample) == 31)
    check(part2(testInputExample) == 29)

    val testInput = readInput("Day12_test")
    println(part1(testInput))
    println(part2(testInput))
}
