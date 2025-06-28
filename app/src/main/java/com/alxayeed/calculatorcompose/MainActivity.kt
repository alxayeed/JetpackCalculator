package com.alxayeed.calculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Stack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {



                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(color = Color(0xFF223344), shape = RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        HighlightedInputText(input=input.ifEmpty { "0" })

                        Text(
                            text = result,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color(0xFF4CAF50), // nice green for result
                            modifier = Modifier.align(Alignment.BottomEnd)
                        )
                    }

            SimpleCalculator(
                input = input,
                onInputChange = { input = it },
                onResultChange = { result = it }
            )





        }
    }
}

@Composable
fun CalculatorButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(80.dp)
    ) {
        Text(label, fontSize = 40.sp)
    }
}



@Composable
fun HighlightedInputText(input: String) {
    val operators = setOf('+', '-', '*', '/')

    val annotatedString = buildAnnotatedString {
        input.forEach { char ->
            if (char in operators) {
                withStyle(style = SpanStyle(color = Color(0xFFFF5722))) { // example: orange color for operators
                    append(char)
                }
            } else {
                append(char)
            }
        }
    }

    Text(
        text = annotatedString.ifEmpty { AnnotatedString("0") },
        style = MaterialTheme.typography.headlineMedium,
        color = Color.White,
    )
}


fun eval(expression: String): Double {
    val tokens = tokenize(expression)
    val rpn = shuntingYard(tokens)
    return evaluateRPN(rpn)
}

fun tokenize(expr: String): List<String> {
    val regex = Regex("""(\d+(\.\d+)?|[-+*/()])""")
    return regex.findAll(expr.replace(" ", "")).map { it.value }.toList()
}

fun shuntingYard(tokens: List<String>): List<String> {
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
            else -> stack.push(token.toDouble())
        }
    }
    return stack.pop()
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    CalculatorApp()
}
