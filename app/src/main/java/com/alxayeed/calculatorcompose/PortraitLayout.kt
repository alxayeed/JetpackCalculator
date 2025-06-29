package com.alxayeed.calculatorcompose

import ClearButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alxayeed.calculatorcompose.components.DisplayPanel
import com.alxayeed.calculatorcompose.components.ModeToggle


@Composable
fun PortraitLayout(
    input: String,
    result: String,
    isAdvanced: Boolean,
    onInputChange: (String) -> Unit,
    onResultChange: (String) -> Unit,
    onModeToggle: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        DisplayPanel(input, result)

        Row(verticalAlignment = Alignment.CenterVertically) {
            ClearButton(onClick = onClear)
            ModeToggle(isAdvanced = isAdvanced, onToggle = onModeToggle)
        }

        if (isAdvanced)
            AdvancedCalculator(input, onInputChange, onResultChange)
        else
            SimpleCalculator(input, onInputChange, onResultChange)
    }
}
