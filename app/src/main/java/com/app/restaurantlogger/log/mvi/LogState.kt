package com.app.restaurantlogger.log.mvi

import com.app.restaurantlogger.mvi.BaseState
import com.app.restaurantlogger.mvi.FetchStatus
import javax.annotation.concurrent.Immutable

@Immutable
data class LogState(
    override val fetchStatus: FetchStatus,
    val logData: LogData
) : BaseState {
    companion object {
        fun initial() = LogState(
            fetchStatus = FetchStatus.Fetching,
            logData = LogData.initial()
        )
    }
}