package com.app.restaurantlogger.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.colorResource
import com.app.restaurantlogger.R

@Composable
fun RestaurantLoggerTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    val colors =
        if (darkTheme) {
            appDarkTheme()
        } else {
            appLightTheme()
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

@Composable
fun appDarkTheme(): ColorScheme =
    darkColorScheme(
        primary = colorResource(id = R.color.dark_primary_color),
        onPrimary = colorResource(id = R.color.dark_on_primary),
        primaryContainer = colorResource(id = R.color.dark_primary_variant),
        secondary = colorResource(id = R.color.dark_secondary),
        onSecondary = colorResource(id = R.color.dark_on_secondary),
        secondaryContainer = colorResource(id = R.color.dark_secondary_variant),
        background = colorResource(id = R.color.dark_background),
        onBackground = colorResource(id = R.color.dark_on_background),
        surface = colorResource(id = R.color.dark_surface),
        onSurface = colorResource(id = R.color.dark_on_surface),
        error = colorResource(id = R.color.dark_error),
        onError = colorResource(id = R.color.dark_on_error),
    )

@Composable
fun appLightTheme(): ColorScheme =
    lightColorScheme(
        primary = colorResource(id = R.color.light_primary_color),
        onPrimary = colorResource(id = R.color.light_on_primary),
        primaryContainer = colorResource(id = R.color.light_primary_variant),
        secondary = colorResource(id = R.color.light_secondary),
        onSecondary = colorResource(id = R.color.light_on_secondary),
        secondaryContainer = colorResource(id = R.color.light_secondary_variant),
        background = colorResource(id = R.color.light_background),
        onBackground = colorResource(id = R.color.light_on_background),
        surface = colorResource(id = R.color.light_surface),
        onSurface = colorResource(id = R.color.light_on_surface),
        error = colorResource(id = R.color.light_error),
        onError = colorResource(id = R.color.light_on_error),
    )
