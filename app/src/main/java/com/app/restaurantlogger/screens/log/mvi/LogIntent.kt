package com.app.restaurantlogger.screens.log.mvi

import com.app.restaurantlogger.mvi.BaseIntent

sealed class LogIntent : BaseIntent {
    object LoadingData : LogIntent()

    data class UpdateData(val data: LogData) : LogIntent()

    object ShowLogSheet : LogIntent()

    object HideLogSheet : LogIntent()
}
