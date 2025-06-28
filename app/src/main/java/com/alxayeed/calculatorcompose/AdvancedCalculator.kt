package com.alxayeed.calculatorcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alxayeed.calculatorcompose.components.CalculatorButton
import com.alxayeed.calculatorcompose.ui.theme.Red300

@Composable
fun AdvancedCalculator(
    input: String,
    onInputChange: (String) -> Unit,
    onResultChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {


        val advancedButtonLayout = listOf(
            listOf("(", ")", "%", "^"),
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("⌫", "0", "=", "+")
        )

        for (row in advancedButtonLayout) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (label in row) {
                    CalculatorButton(
                        label = label,
                        size = 70,
                        input = input,
                        onInputChange = onInputChange,
                        onResultChange = onResultChange,
                        fontSize = if (label == "⌫") 20 else 40,
                    )
                }
            }
        }


    }
}
