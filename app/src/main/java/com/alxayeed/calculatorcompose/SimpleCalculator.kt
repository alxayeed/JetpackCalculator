package com.alxayeed.calculatorcompose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alxayeed.calculatorcompose.components.CalculatorButton
import com.alxayeed.calculatorcompose.utils.ScreenUtils

@Composable
fun SimpleCalculator(
    input: String,
    onInputChange: (String) -> Unit,
    onResultChange: (String) -> Unit
) {
    val isPortrait = ScreenUtils.isPortrait()

    val buttonRows: List<List<String>> = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("⌫", "0", "=", "+")
    )

    val columns = buttonRows.maxOf { it.size }

    // Spacing values differ by orientation:
    val verticalSpacing = if (isPortrait) 12.dp else 4.dp
    val horizontalSpacing = if (isPortrait) 12.dp else 4.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing)
    ) {
        for (row in buttonRows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
            ) {
                for (label in row) {
                    CalculatorButton(
                        label = label,
                        input = input,
                        onInputChange = onInputChange,
                        onResultChange = onResultChange,
                        fontSize = if (label == "⌫") 26 else 40,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                }
                // Fill empty spaces at the end of the row if needed
                repeat(columns - row.size) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                }
            }
        }
    }
}


