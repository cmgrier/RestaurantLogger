package com.app.restaurantlogger.screens.log.mvi

import com.app.restaurantlogger.mvi.BaseState
import com.app.restaurantlogger.mvi.FetchStatus
import javax.annotation.concurrent.Immutable

@Immutable
data class LogState(
    override val fetchStatus: FetchStatus,
    val logData: LogData,
    val showLogSheet: Boolean,
) : BaseState {
    companion object {
        fun initial() =
            LogState(
                fetchStatus = FetchStatus.NotFetched,
                logData = LogData.initial(),
                showLogSheet = false,
            )
    }
}
