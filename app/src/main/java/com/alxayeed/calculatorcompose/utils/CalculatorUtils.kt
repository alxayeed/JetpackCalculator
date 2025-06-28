package com.alxayeed.calculatorcompose.utils

import java.util.Stack

object CalculatorUtils {

    fun eval(expression: String): Double {
        val tokens = tokenize(expression)
        val rpn = shuntingYard(tokens)
        return evaluateRPN(rpn)
    }

    private fun tokenize(expr: String): List<String> {
        val regex = Regex("""(\d+(\.\d+)?|[-+*/()])""")
        return regex.findAll(expr.replace(" ", "")).map { it.value }.toList()
    }

    private fun shuntingYard(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val ops = Stack<String>()
        val precedence = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2)

        for (token in tokens) {
            when {
                token.matches(Regex("""\d+(\.\d+)?""")) -> output += token
                token in precedence -> {
                    while (ops.isNotEmpty() && ops.peek() in precedence &&
                        precedence[ops.peek()]!! >= precedence[token]!!
                    ) {
                        output += ops.pop()
                    }
                    ops.push(token)
                }
                token == "(" -> ops.push(token)
                token == ")" -> {
                    while (ops.isNotEmpty() && ops.peek() != "(") {
                        output += ops.pop()
                    }
                    if (ops.isEmpty() || ops.peek() != "(") throw IllegalArgumentException("Mismatched parentheses")
                    ops.pop()
                }
            }
        }

        while (ops.isNotEmpty()) {
            val op = ops.pop()
            if (op == "(" || op == ")") throw IllegalArgumentException("Mismatched parentheses")
            output += op
        }

        return output
    }

    private fun evaluateRPN(rpn: List<String>): Double {
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
                else -> stack.push(token.toDouble())
            }
        }
        return stack.pop()
    }
}
