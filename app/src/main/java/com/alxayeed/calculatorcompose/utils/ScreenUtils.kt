package com.alxayeed.calculatorcompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration

import kotlin.math.min

object ScreenUtils {

    @Composable
    fun isPortrait(): Boolean {
        val configuration = LocalConfiguration.current
        return configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    /**
     * Dynamically calculates the button size to fit the screen
     * without scroll, based on the number of columns and rows.
     *
     * @param columns Number of buttons per row
     * @param rows Number of rows
     * @param verticalPadding Padding to subtract from height (in dp)
     * @param horizontalPadding Padding to subtract from width (in dp)
     */
    @Composable
    fun calculateButtonSize(
        columns: Int,
        rows: Int,
        verticalPadding: Int = 100,
        horizontalPadding: Int = 32
    ): Int {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        val screenHeight = configuration.screenHeightDp

        val maxButtonWidth = (screenWidth - horizontalPadding) / columns
        val maxButtonHeight = (screenHeight - verticalPadding) / rows

        return if (isPortrait())
            min(maxButtonWidth, maxButtonHeight)
        else
            maxButtonWidth
    }

}
