package com.app.restaurantlogger.mvi

interface BaseState {
    val fetchStatus: FetchStatus
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}