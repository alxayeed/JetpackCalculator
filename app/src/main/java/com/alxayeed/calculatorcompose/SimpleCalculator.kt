package com.alxayeed.calculatorcompose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alxayeed.calculatorcompose.components.CalculatorButton

@Composable
fun SimpleCalculator(
    input: String,
    onInputChange: (String) -> Unit,
    onResultChange: (String) -> Unit
) {
    val buttonLayout = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("C", "0", "=", "+")
    )

    Column {
        for (row in buttonLayout) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top,
            ) {
                for (label in row) {
                    CalculatorButton(
                        label = label,
                        input = input,
                        onInputChange = onInputChange,
                        onResultChange = onResultChange
                    )
                }
            }
        }
    }
}
