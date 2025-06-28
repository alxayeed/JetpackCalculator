package com.alxayeed.calculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

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
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = input.ifEmpty { "0" },
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1
                )
            }


            Spacer(modifier = Modifier.height(16.dp))



            Spacer(modifier = Modifier.height(250.dp))

            Text(
                text = "Result: $result",
                style = MaterialTheme.typography.headlineLarge
            )


            val buttonLayout = listOf(
                listOf("7", "8", "9", "/"),
                listOf("4", "5", "6", "*"),
                listOf("1", "2", "3", "-"),
                listOf("C", "0", "=", "+")
            )

            for (row in buttonLayout) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (label in row) {
                        CalculatorButton(label) {
                            when (label) {
                                "=" -> {
                                    result = try {
                                        eval(input).toString()
                                    } catch (e: Exception) {
                                        "Error: $e"
                                    }
                                }
                                "C" -> {
                                    input = ""
                                    result = "0"
                                }
                                else -> {
                                    input += label
                                }
                            }
                        }
                    }
                }
            }
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
