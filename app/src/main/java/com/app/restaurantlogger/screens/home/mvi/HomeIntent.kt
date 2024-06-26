package com.app.restaurantlogger.screens.home.mvi

import com.app.restaurantlogger.mvi.BaseIntent

sealed class HomeIntent : BaseIntent {
    object LoadingData : HomeIntent()

    data class UpdateData(val data: HomeData) : HomeIntent()
}
