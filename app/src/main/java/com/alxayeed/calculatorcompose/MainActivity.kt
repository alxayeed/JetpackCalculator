package com.alxayeed.calculatorcompose

import ClearButton
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alxayeed.calculatorcompose.components.ModeToggle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorApp() {
    var input by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("0") }
    var isAdvanced by rememberSaveable { mutableStateOf(false) }


    Scaffold {
        paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {


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
                Row ( verticalAlignment = Alignment.CenterVertically ){
                    ClearButton(
                        onClick = {
                            input = ""
                            result = "0"
                        },
                    )

                    ModeToggle(
                        isAdvanced = isAdvanced,
                        onToggle = { isAdvanced = !isAdvanced }
                    )
                }
            }


            if (isAdvanced) {
                AdvancedCalculator(input, onInputChange = { input = it }, onResultChange = { result = it })
            } else {
                SimpleCalculator(input, onInputChange = { input = it }, onResultChange = { result = it })
            }

        }
    }
}





@Composable
fun HighlightedInputText(input: String) {
    val operators = setOf('+', '-', '*', '/', '(', ')', '%', '^')

    val annotatedString = buildAnnotatedString {
        input.forEach { char ->
            if (char in operators) {
                withStyle(style = SpanStyle(color = Color(0xFFFF5722))) { // example: orange color for operators
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


@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    CalculatorApp()
}
