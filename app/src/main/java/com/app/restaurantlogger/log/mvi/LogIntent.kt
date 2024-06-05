package com.app.restaurantlogger.log.mvi

import android.util.Log
import com.app.restaurantlogger.mvi.BaseIntent

sealed class LogIntent: BaseIntent {
    object LoadingData: LogIntent()
    data class UpdateData(val data: LogData): LogIntent()
    object ShowNewLogSheet: LogIntent()
    object HideNewLogSheet: LogIntent()
}