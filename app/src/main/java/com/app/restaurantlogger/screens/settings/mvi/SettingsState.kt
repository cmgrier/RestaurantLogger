package com.app.restaurantlogger.screens.settings.mvi

import com.app.restaurantlogger.mvi.BaseState
import com.app.restaurantlogger.mvi.FetchStatus

data class SettingsState(
    override val fetchStatus: FetchStatus,
    val settingsData: SettingsData,
) : BaseState {
    companion object {
        fun initial() =
            SettingsState(
                fetchStatus = FetchStatus.NotFetched,
                settingsData = SettingsData.initial(),
            )
    }
}
