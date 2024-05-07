package com.app.restaurantlogger.log.mvi

import com.app.restaurantlogger.mvi.BaseIntent

sealed class LogIntent: BaseIntent {
    object LoadingData: LogIntent()
    data class UpdateData(val data: LogData): LogIntent()
}