package com.app.restaurantlogger.screens.settings.mvi

import com.app.restaurantlogger.mvi.BaseIntent

sealed class SettingsIntent : BaseIntent {
    data class UpdateData(val data: SettingsData) : SettingsIntent()
}
