package com.app.restaurantlogger.screens.settings.mvi

import com.app.restaurantlogger.mvi.BaseData

data class SettingsData(
    val isDarkTheme: Boolean,
) : BaseData {
    override val name: String = "Settings"

    companion object {
        fun initial() =
            SettingsData(
                isDarkTheme = false,
            )
    }
}
