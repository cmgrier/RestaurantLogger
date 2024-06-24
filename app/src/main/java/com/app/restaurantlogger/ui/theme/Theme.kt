package com.app.restaurantlogger.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val DarkColorPalette = darkColorScheme()

private val LightColorPalette = lightColorScheme()

@Composable
fun RestaurantLoggerTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val colors = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (darkTheme) {
            dynamicDarkColorScheme(context = context)
        } else {
            dynamicLightColorScheme(context = context)
        }
    } else {
        if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        }
    }

    CompositionLocalProvider(
        LocalEdgePadding provides defaultEdgeSpacing,
        LocalSheetBottomPadding provides defaultSheetBottomSpacing,
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = content,
        )
    }
}
