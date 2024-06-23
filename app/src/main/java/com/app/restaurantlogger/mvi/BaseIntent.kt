package com.app.restaurantlogger.mvi

interface BaseIntent {
    data class UpdateData(val data: BaseData) : BaseIntent
}
