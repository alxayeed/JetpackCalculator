package com.alxayeed.calculatorcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DisplayPanel(input: String, result: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(color = Color(0xFF223344), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        HighlightedInputText(input = input.ifEmpty { "0" })

        Text(
            text = result,
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFF4CAF50),
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
