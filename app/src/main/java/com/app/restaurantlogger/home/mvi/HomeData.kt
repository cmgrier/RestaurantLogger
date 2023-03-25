package com.app.restaurantlogger.home.mvi

import com.app.restaurantlogger.dataModel.Restaurant
import com.app.restaurantlogger.dataModel.sampleRestaurantList
import com.app.restaurantlogger.mvi.BaseData

data class HomeData(
    override val name: String = "Home Data",
    val restaurants : List<Restaurant>
): BaseData {
    companion object {
        fun intitial() = HomeData(
            restaurants = sampleRestaurantList
        )
    }
}