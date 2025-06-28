package com.alxayeed.calculatorcompose.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alxayeed.calculatorcompose.util.CalculatorUtils

@Composable
fun CalculatorButton(
    label: String,
    input: String,
    onInputChange: (String) -> Unit,
    onResultChange: (String) -> Unit,
    size: Int = 80,
    fontSize: Int = 40,
) {
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

                "C" -> {
                    onInputChange("")
                    onResultChange("0")
                }

                "Del" -> {
                    if (input.isNotEmpty()) {
                        onInputChange(input.dropLast(1))
                    }
                }



                else -> {
                    onInputChange(input + label)
                }
            }
        },
        modifier = Modifier.size(size.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (label) {
                "⌫" -> Color.Gray
                "=" -> Color(0xFF198754)
                else -> Color.Unspecified
            }
        )

    ) {
        Text(label, fontSize = fontSize.sp)
    }
}
