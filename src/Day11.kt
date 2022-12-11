import java.math.BigInteger

data class Monkey(
    val starting: List<Long>,
    val operation: List<String>,
    val divisor: Long,
    val monkeyTrue: Int,
    val monkeyFalse: Int
)

fun main() {
    fun simulation(
        monkeys: Array<Monkey>,
        state: Array<List<Long>>,
        inspections: Array<Long>,
        n: Int
    ) {
        val relax = if (n == 20) 3 else 1
        val p = monkeys.map { it.divisor }.fold(1L) { a, b -> a * b }
        for (i in 1..n) {
            for (k in monkeys.indices) {
                val monkey = monkeys[k]
                val items = state[k]
                for (item in items) {
                    val factor = if (monkey.operation[2] == "old") item else monkey.operation[2].toLong()
                    val level = (if (monkey.operation[1] == "*") item * factor else item + factor) / relax

                    if ((level % monkey.divisor) == 0L) {
                        state[monkey.monkeyTrue] = state[monkey.monkeyTrue] + level % p
                    } else {
                        state[monkey.monkeyFalse] = state[monkey.monkeyFalse] + level % p
                    }
                    inspections[k]++
                }
                state[k] = listOf()
            }
        }
    }

    fun analysis(input: String, n: Int): Long {
        val monkeys = input
            .split("\n\n")
            .map { entry ->
                val lines = entry.lines()

                val items = lines[1]
                    .substringAfter("  Starting items: ")
                    .split(",")
                    .map { it.trim().toLong() }

                val operation = lines[2]
                    .substringAfter("  Operation: new = ")
                    .split(" ")

                val divisor = lines[3]
                    .substringAfter("  Test: divisible by ")
                    .toLong()

                val monkeyTrue = lines[4]
                    .substringAfter("    If true: throw to monkey ")
                    .toInt()

                val monkeyFalse = lines[5]
                    .substringAfter("    If false: throw to monkey ")
                    .toInt()

                Monkey(items, operation, divisor, monkeyTrue, monkeyFalse)
            }
            .toTypedArray()

        val state = monkeys.map { it.starting }.toTypedArray()
        val inspections = monkeys.map { 0L }.toTypedArray()

        simulation(monkeys, state, inspections, n)

        val sorted = inspections.sortedDescending()
        return sorted[0] * sorted[1]
    }

    fun part1(input: String): Long {
        return analysis(input, 20);
    }

    fun part2(input: String): Long {
        return analysis(input, 10000);
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readText("Day11_example")
    check(part1(testInputExample) == 10605L)
    check(part2(testInputExample) == 2713310158L)

    val testInput = readText("Day11_test")
    println(part1(testInput))
    println(part2(testInput))
}
