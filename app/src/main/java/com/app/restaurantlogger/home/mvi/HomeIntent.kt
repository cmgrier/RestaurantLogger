package com.app.restaurantlogger.home.mvi

import com.app.restaurantlogger.mvi.BaseIntent

sealed class HomeIntent: BaseIntent {
    object Initialize: HomeIntent()
    data class UpdateData(val data: HomeData): HomeIntent()
}