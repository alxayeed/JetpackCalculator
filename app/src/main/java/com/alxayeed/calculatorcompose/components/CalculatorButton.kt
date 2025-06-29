package com.alxayeed.calculatorcompose.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.alxayeed.calculatorcompose.utils.CalculatorUtils

@Composable
fun CalculatorButton(
    label: String,
    input: String,
    onInputChange: (String) -> Unit,
    onResultChange: (String) -> Unit,
    modifier: Modifier = Modifier,            // ✅ replace fixed size with modifier
    fontSize: Int = 40
) {
    val operatorLabels = setOf("+", "-", "*", "/", "%", "^", "√", "!")

    val buttonColor = when (label) {
        "⌫" -> Color.Gray
        "=" -> Color(0xFF198754)
        in operatorLabels -> Color(0xFFFF9800)
        else -> Color.Unspecified
    }

    Button(
        onClick = {
            when (label) {
                "=" -> {
                    onResultChange(
                        try {
                            CalculatorUtils.eval(input).toString()
                        } catch (e: Exception) {
                            "Error: $e"
                        }
                    )
                }

                "C", "Del", "⌫" -> {
                    if (input.isNotEmpty()) {
                        onInputChange(input.dropLast(1))
                    }
                }

                else -> {
                    onInputChange(input + label)
                }
            }
        },
        modifier = modifier,                    // ✅ use injected modifier
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Text(label, fontSize = fontSize.sp)
    }
}
