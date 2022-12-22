sealed interface Expression {
    data class Add(val left: Expression, val right: Expression) : Expression
    data class Mul(val left: Expression, val right: Expression) : Expression
    data class Divide(val left: Expression, val right: Expression) : Expression
    data class Sub(val left: Expression, val right: Expression) : Expression
    data class Value(val value: Long) : Expression
    data class Monkey(val monkey: String) : Expression
}

fun main() {
    fun part1(input: List<String>): Long {
        val expressions = input.associate { line ->
            val monkey = line.substringBefore(": ")
            val expression = line.substringAfter(": ").split(' ')
            monkey to when {
                expression.size == 1 -> {
                    Expression.Value(expression[0].toLong())
                }

                expression[1] == "+" -> {
                    Expression.Add(Expression.Monkey(expression[0]), Expression.Monkey(expression[2]))
                }

                expression[1] == "-" -> {
                    Expression.Sub(Expression.Monkey(expression[0]), Expression.Monkey(expression[2]))
                }

                expression[1] == "*" -> {
                    Expression.Mul(Expression.Monkey(expression[0]), Expression.Monkey(expression[2]))
                }

                expression[1] == "/" -> {
                    Expression.Divide(Expression.Monkey(expression[0]), Expression.Monkey(expression[2]))
                }

                else -> {
                    throw IllegalStateException()
                }
            }
        }

        fun calculate(expression: Expression): Long = when (expression) {
            is Expression.Add -> calculate(expression.left) + calculate(expression.right)
            is Expression.Divide -> calculate(expression.left) / calculate(expression.right)
            is Expression.Monkey -> calculate(expressions.getValue(expression.monkey))
            is Expression.Mul -> calculate(expression.left) * calculate(expression.right)
            is Expression.Sub -> calculate(expression.left) - calculate(expression.right)
            is Expression.Value -> expression.value
        }

        val result = calculate(expressions.getValue("root"))
        return result
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInputExample = readInput("Day21_example")
    check(part1(testInputExample) == 152L)
    check(part2(testInputExample) == 0L)

    val testInput = readInput("Day21_test")
    println(part1(testInput))
    println(part2(testInput))
}
