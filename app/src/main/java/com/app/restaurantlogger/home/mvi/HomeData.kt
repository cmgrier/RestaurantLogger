package com.app.restaurantlogger.home.mvi

import com.app.restaurantlogger.dataModel.Restaurant
import com.app.restaurantlogger.home.ui.Filters
import com.app.restaurantlogger.home.ui.SortMethod
import com.app.restaurantlogger.mvi.BaseData

data class HomeData(
    override val name: String = "Home Data",
    val restaurants: List<Restaurant>,
    val sortMethod: SortMethod,
    val selectedFilters: Filters,
    val showAddPlaceSheet: Boolean,
    val showSortSheet: Boolean,
    val showFilterSheet: Boolean,
): BaseData {
    companion object {
        fun initial() = HomeData(
            restaurants = emptyList(),
            sortMethod = SortMethod.Name,
            showAddPlaceSheet = false,
            showSortSheet = false,
            showFilterSheet = false,
            selectedFilters = Filters(
                ratingLowerLimit = 0f,
                ratingUpperLimit = 5f,
            ),
        )
    }
}