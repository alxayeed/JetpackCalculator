package com.alxayeed.calculatorcompose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alxayeed.calculatorcompose.components.CalculatorButton

@Composable
fun AdvancedCalculator(
    input: String,
    onInputChange: (String) -> Unit,
    onResultChange: (String) -> Unit
) {
    val advancedButtonLayout = listOf(
        listOf("√", "!", "%", "^"),
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("⌫", "0", "=", "+"),
    )

    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        for (row in advancedButtonLayout) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (label in row) {
                    CalculatorButton(
                        label = label,
                        input = input,
                        onInputChange = onInputChange,
                        onResultChange = onResultChange,
                        fontSize = if (label == "⌫") 20 else 40,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}
