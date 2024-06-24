package com.app.restaurantlogger.screens.home.mvi

import com.app.restaurantlogger.dataModel.Restaurant
import com.app.restaurantlogger.mvi.BaseData
import com.app.restaurantlogger.screens.home.ui.Filters
import com.app.restaurantlogger.screens.home.ui.SortMethod

data class HomeData(
    override val name: String = "Home Data",
    val restaurants: List<Restaurant>,
    val sortMethod: SortMethod,
    val selectedFilters: Filters,
    val showPlaceSheet: Boolean,
    val showSortSheet: Boolean,
    val showFilterSheet: Boolean,
) : BaseData {
    companion object {
        fun initial() =
            HomeData(
                restaurants = emptyList(),
                sortMethod = SortMethod.Name,
                showPlaceSheet = false,
                showSortSheet = false,
                showFilterSheet = false,
                selectedFilters =
                    Filters(
                        ratingLowerLimit = 0f,
                        ratingUpperLimit = 5f,
                    ),
            )
    }
}
