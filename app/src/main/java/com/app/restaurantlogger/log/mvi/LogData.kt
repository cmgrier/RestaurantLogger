package com.app.restaurantlogger.log.mvi

import com.app.restaurantlogger.dataModel.Restaurant
import com.app.restaurantlogger.dataModel.sampleRestaurant
import com.app.restaurantlogger.mvi.BaseData

data class LogData(
    override val name: String = "Log Data",
    val restaurant: Restaurant
): BaseData {
    companion object {
        fun initial() = LogData(
            restaurant = sampleRestaurant
        )
    }
}