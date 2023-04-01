package com.app.restaurantlogger.home.mvi

import androidx.compose.ui.graphics.Color
import com.app.restaurantlogger.dataModel.Restaurant
import com.app.restaurantlogger.mvi.BaseIntent

sealed class HomeIntent: BaseIntent {
    object Initialize: HomeIntent()
    data class UpdateData(val data: HomeData): HomeIntent()
}