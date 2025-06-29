package com.alxayeed.calculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alxayeed.calculatorcompose.utils.ScreenUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CalculatorApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorApp() {
    var input by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("0") }
    var isAdvanced by rememberSaveable { mutableStateOf(false) }

    val isPortrait = ScreenUtils.isPortrait()

    Scaffold { paddingValues ->
        if (isPortrait) {
            PortraitLayout(
                input = input,
                result = result,
                isAdvanced = isAdvanced,
                onInputChange = { input = it },
                onResultChange = { result = it },
                onModeToggle = { isAdvanced = !isAdvanced },
                onClear = {
                    input = ""
                    result = "0"
                },
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            )
        } else {
            LandscapeLayout(
                input = input,
                result = result,
                isAdvanced = isAdvanced,
                onInputChange = { input = it },
                onResultChange = { result = it },
                onModeToggle = { isAdvanced = !isAdvanced },
                onClear = {
                    input = ""
                    result = "0"
                },
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            )
        }
    }
}
