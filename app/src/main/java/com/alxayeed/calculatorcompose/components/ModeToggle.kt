package com.alxayeed.calculatorcompose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ModeToggle(
    isAdvanced: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Advanced Mode",
            color = Color(0xFF223344),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onToggle) {
            Icon(
                imageVector = if (isAdvanced) Icons.Filled.ToggleOn else Icons.Filled.ToggleOff,
                contentDescription = "Toggle Mode",
                tint = if (isAdvanced) Color(0xFF4CAF50) else Color.Gray,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
