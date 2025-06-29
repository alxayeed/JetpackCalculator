package com.alxayeed.calculatorcompose.utils

import java.util.Stack
import kotlin.math.pow
import kotlin.math.sqrt

object CalculatorUtils {

    private val precedence = mapOf(
        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2,
        "%" to 2,
        "^" to 3,
        "√" to 4,
        "!" to 4
    )

    /**
     * Tokenizes the input expression into numbers and operators.
     */
    fun tokenize(expr: String): List<String> {
        val regex = Regex("""(\d+(\.\d+)?|[+\-*/%^√!])""")
        return regex.findAll(expr.replace(" ", "")).map { it.value }.toList()
    }

    /**
     * Converts infix tokens to postfix using the Shunting Yard algorithm.
     */
    fun shuntingYard(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val ops = Stack<String>()

        for (token in tokens) {
            when {
                token.matches(Regex("""\d+(\.\d+)?""")) -> output += token
                token in precedence -> {
                    while (ops.isNotEmpty() && ops.peek() in precedence) {
                        val top = ops.peek()
                        if (
                            (precedence[top]!! > precedence[token]!!) ||
                            (precedence[top] == precedence[token] && token != "^")
                        ) {
                            output += ops.pop()
                        } else break
                    }
                    ops.push(token)
                }
                else -> throw IllegalArgumentException("Invalid token: $token")
            }
        }

        while (ops.isNotEmpty()) {
            output += ops.pop()
        }

        return output
    }

    /**
     * Evaluates the postfix (RPN) expression.
     */
    fun evaluateRPN(rpn: List<String>): Double {
        val stack = Stack<Double>()
        for (token in rpn) {
            when (token) {
                "+" -> stack.push(stack.pop() + stack.pop())
                "-" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a - b)
                }
                "*" -> stack.push(stack.pop() * stack.pop())
                "/" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a / b)
                }
                "%" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a % b)
                }
                "^" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a.pow(b))
                }
                "√" -> {
                    val a = stack.pop()
                    stack.push(sqrt(a))
                }
                "!" -> {
                    val a = stack.pop()
                    stack.push(factorial(a.toInt()))
                }
                else -> stack.push(token.toDouble())
            }
        }
        if (stack.size != 1) {
            throw IllegalArgumentException("Invalid expression")
        }
        return stack.pop()
    }

    private fun factorial(n: Int): Double {
        require(n >= 0) { "Factorial is not defined for negative numbers" }
        return if (n <= 1) 1.0 else n * factorial(n - 1)
    }

    fun eval(expression: String): Double {
        val tokens = tokenize(expression)
        val rpn = shuntingYard(tokens)
        return evaluateRPN(rpn)
    }
}
