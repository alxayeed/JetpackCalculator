package com.alxayeed.calculatorcompose.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun HighlightedInputText(input: String) {
    val operators = setOf('+', '-', '*', '/', '(', ')', '%', '^')

    val annotatedString = buildAnnotatedString {
        input.forEach { char ->
            if (char in operators) {
                withStyle(style = SpanStyle(color = Color(0xFFFF5722))) {
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
