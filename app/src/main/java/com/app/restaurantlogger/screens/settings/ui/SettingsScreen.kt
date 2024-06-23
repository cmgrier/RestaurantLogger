package com.app.restaurantlogger.screens.settings.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.PREFERENCES_FILE_NAME
import com.app.restaurantlogger.screens.settings.mvi.SettingsViewModel
import com.app.restaurantlogger.ui.enterZoomLeft
import com.app.restaurantlogger.ui.exitZoomLeft
import com.app.restaurantlogger.ui.exitZoomRight
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsScreen(settingsViewModel: SettingsViewModel) {
    composable(
        route = AppScreen.Settings.name,
        enterTransition = {
            enterZoomLeft()
        },
        exitTransition = {
            exitZoomLeft()
        },
        popExitTransition = {
            exitZoomRight()
        },
    ) {
        settingsViewModel.refreshState(
            LocalContext.current.getSharedPreferences(
                PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE,
            ),
        )
        SettingsScreen(
            settingsViewModel = settingsViewModel,
        )
    }
}

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    val state by settingsViewModel.state.collectAsState()
    val context = LocalContext.current
    LazyColumn(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalEdgePadding.current),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            val isDarkTheme = state.settingsData.isDarkTheme
            SettingRow(
                title = if (isDarkTheme) "Dark Theme" else "Light Theme",
                isSelected = isDarkTheme,
                onSelectChange = { newState ->
                    context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)?.let {
                        settingsViewModel.updateSettings(
                            newData =
                                state.settingsData.copy(
                                    isDarkTheme = newState,
                                ),
                            preferences = it,
                        )
                    }
                    Toast.makeText(
                        context,
                        "Restart the app for the changes to take effect",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }
}
