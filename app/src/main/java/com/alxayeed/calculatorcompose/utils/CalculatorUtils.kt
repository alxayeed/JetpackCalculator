package com.alxayeed.calculatorcompose.util

import java.util.Stack
import kotlin.math.pow

object CalculatorUtils {

    private val precedence = mapOf(
        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2,
        "%" to 2,
        "^" to 3
    )

    /**
     * Tokenizes the input expression into numbers, operators, and parentheses.
     */
    fun tokenize(expr: String): List<String> {
        val regex = Regex("""(\d+(\.\d+)?|[-+*/%^()])""")
        return regex.findAll(expr.replace(" ", "")).map { it.value }.toList()
    }

    /**
     * Converts the list of tokens from infix to postfix notation using
     * the Shunting Yard algorithm.
     */
    fun shuntingYard(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val ops = Stack<String>()

        for (token in tokens) {
            when {
                token.matches(Regex("""\d+(\.\d+)?""")) -> output += token
                token in precedence -> {
                    while (ops.isNotEmpty() && ops.peek() in precedence) {
                        val top = ops.peek()!!
                        // Right-associative for "^", left-associative for others
                        if ((precedence[top]!! > precedence[token]!!) ||
                            (precedence[top] == precedence[token] && token != "^")
                        ) {
                            output += ops.pop()
                        } else {
                            break
                        }
                    }
                    ops.push(token)
                }
                token == "(" -> ops.push(token)
                token == ")" -> {
                    while (ops.isNotEmpty() && ops.peek() != "(") {
                        output += ops.pop()
                    }
                    if (ops.isEmpty() || ops.peek() != "(") {
                        throw IllegalArgumentException("Mismatched parentheses")
                    }
                    ops.pop() // Remove "(" from stack
                }
            }
        }

        while (ops.isNotEmpty()) {
            val op = ops.pop()
            if (op == "(" || op == ")") {
                throw IllegalArgumentException("Mismatched parentheses")
            }
            output += op
        }

        return output
    }

    /**
     * Evaluates the expression in Reverse Polish Notation (postfix).
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
                else -> stack.push(token.toDouble())
            }
        }
        if (stack.size != 1) {
            throw IllegalArgumentException("Invalid expression")
        }
        return stack.pop()
    }

    /**
     * Evaluates a given expression string.
     */
    fun eval(expression: String): Double {
        val tokens = tokenize(expression)
        val rpn = shuntingYard(tokens)
        return evaluateRPN(rpn)
    }
}
