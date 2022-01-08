package com.vodafone.technicalassessment.presentation.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.vodafone.technicalassessment.presentation.components.CircularIndeterminateProgressBar
import com.vodafone.technicalassessment.utils.Status

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun TechnicalAssessmentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    displayProgressBar: Status? = null,
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            )
            {
                content()

                // loading progress
                val isDisplayed = displayProgressBar == Status.LOADING
                CircularIndeterminateProgressBar(isDisplayed = isDisplayed)
            }
        }
    )
}