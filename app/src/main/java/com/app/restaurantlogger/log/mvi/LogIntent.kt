package com.app.restaurantlogger.log.mvi

import com.app.restaurantlogger.mvi.BaseIntent

sealed class LogIntent: BaseIntent {
    object Initialize: LogIntent()
    data class UpdateData(val data: LogData): LogIntent()
}