package com.app.restaurantlogger.home.mvi

import com.app.restaurantlogger.database.Place
import com.app.restaurantlogger.mvi.BaseData

data class HomeData(
    override val name: String = "Home Data",
    val places: List<Place>,
    val showSheet: Boolean
): BaseData {
    companion object {
        fun intitial() = HomeData(
            places = emptyList(),
            showSheet = false,
        )
    }
}