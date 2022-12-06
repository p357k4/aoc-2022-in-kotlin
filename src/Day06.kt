import java.util.BitSet
import javax.naming.OperationNotSupportedException

fun main() {
    fun part1(input: String): Int {
        for (i in input.indices) {
            val chars = input.substring(i..i + (4 - 1)).toSet()
            if (chars.size == 4) {
                return i + 4
            }
        }

        throw OperationNotSupportedException()
    }

    fun part2(input: String): Int {
        for (i in input.indices) {
            val chars = input.substring(i..i + (14 - 1)).toSet()
            if (chars.size == 14) {
                return i + 14
            }
        }

        throw OperationNotSupportedException()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readText("Day06_test")
    check(part1(testInput) == 1598)
    check(part2(testInput) == 2414)
}
