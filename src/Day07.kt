import java.util.ArrayList

sealed interface Node {}
data class Directory(val name: String, val nodes: MutableList<Node>, val parent: Directory?, var size : Int) : Node
data class File(val name: String, val size: Int) : Node

fun main() {

    fun calculate(directory: Directory): Int {
        var size = 0

        for (node in directory.nodes) {
            size += when (node) {
                is File -> node.size
                is Directory -> calculate(node)
            }
        }

        directory.size = size
        return size
    }

    fun totalSizeOfSmallDirectories(directory: Directory): Int {
        var size = if (directory.size > 100_000) 0 else directory.size
        for (node in directory.nodes) {
            size += when (node) {
                is Directory -> totalSizeOfSmallDirectories(node)
                else -> 0
            }
        }

        return size
    }

    fun directories(root: Directory): List<Directory> {
        return root
            .nodes
            .filterIsInstance<Directory>()
            .flatMap(::directories) + root
    }

    fun smallest(root: Directory, size : Int): Directory {
        val result = directories(root)
            .minBy {
            val distance = it.size - size
            if (distance < 0) Int.MAX_VALUE else distance
        }

        return result;
    }

    fun build(input: List<String>): Directory {
        val root = Directory("", ArrayList<Node>(), null, 0)

        var current: Directory? = root
        for (line in input) {
            val split = line.split(' ')
            if (split[0] == "$" && split[1] == "cd" && split[2] == "/") {
                current = root;
            } else if (split[0] == "$" && split[1] == "cd" && split[2] == "..") {
                current = current?.parent
            } else if (split[0] == "$" && split[1] == "cd") {
                val child = Directory(split[2], ArrayList<Node>(), current, 0)
                current?.nodes?.add(child)
                current = child
            } else if (split[0] == "$" && split[1] == "ls") {
                // ls
            } else if (split[0] == "dir") {
                // dir
            } else {
                // file
                current?.nodes?.add(File(split[1], split[0].toInt()))
            }
        }

        calculate(root)
        return root
    }

    fun part1(input: List<String>): Int {
        val root = build(input)

        return totalSizeOfSmallDirectories(root)
    }

    fun part2(input: List<String>): Int {
        val root = build(input)

        val size = 30_000_000 - (70_000_000 - root.size)
        return smallest(root, size).size
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day07_example")
    check(part1(testInputExample) == 95437)
    check(part2(testInputExample) == 24933642)

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 1077191)
    check(part2(testInput) == 5649896)
}
