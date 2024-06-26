package com.app.restaurantlogger.screens.home.mvi

import com.app.restaurantlogger.mvi.BaseState
import com.app.restaurantlogger.mvi.FetchStatus
import javax.annotation.concurrent.Immutable

@Immutable
data class HomeState(
    override val fetchStatus: FetchStatus,
    val homeData: HomeData,
) : BaseState {
    companion object {
        fun initial() =
            HomeState(
                fetchStatus = FetchStatus.NotFetched,
                homeData = HomeData.initial(),
            )
    }
}
