package com.app.restaurantlogger.screens.settings.mvi

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.SettingNames
import com.app.restaurantlogger.mvi.BaseReducer
import com.app.restaurantlogger.mvi.BaseViewModel
import com.app.restaurantlogger.ui.FloatingActionButtonContent
import com.app.restaurantlogger.ui.TopBarHeader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor() : BaseViewModel<SettingsState, SettingsIntent>() {
        private val reducer = SettingsReducer(SettingsState.initial())
        override val appScreen: AppScreen
            get() = AppScreen.Settings
        override val state: StateFlow<SettingsState>
            get() = reducer.state

        override val data: SettingsData
            get() = state.value.settingsData
        override val floatingActionButtonAction: () -> Unit
            get() = {}
        override val floatingActionButton: @Composable () -> Unit
            get() = { FloatingActionButtonContent("Save") }
        override val topBarContent: @Composable (NavHostController, Modifier) -> Unit
            get() = { navHostController, modifier ->
                TopBarHeader(
                    header = data.name,
                    modifier = modifier,
                    hasBackButton = true,
                    navHostController = navHostController,
                )
            }

        fun refreshState(preferences: SharedPreferences) {
            val newData =
                SettingsData(
                    isDarkTheme = preferences.getBoolean(SettingNames.IsDarkTheme.name, false),
                )
            reducer.sendEvent(SettingsIntent.UpdateData(newData))
        }

        fun updateSettings(
            newData: SettingsData,
            preferences: SharedPreferences,
        ) {
            reducer.sendEvent(SettingsIntent.UpdateData(newData))
            with(preferences.edit()) {
                this.putBoolean(SettingNames.IsDarkTheme.name, newData.isDarkTheme)
                apply()
            }
        }

        class SettingsReducer(initialState: SettingsState) :
            BaseReducer<SettingsState, SettingsIntent>(initialState) {
            override fun reduce(
                oldState: SettingsState,
                event: SettingsIntent,
            ) {
                when (event) {
                    is SettingsIntent.UpdateData -> {
                        setState(oldState.copy(settingsData = event.data))
                    }
                }
            }
        }
    }
