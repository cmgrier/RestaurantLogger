package com.app.restaurantlogger.home.mvi

import androidx.lifecycle.viewModelScope
import com.app.restaurantlogger.mvi.BaseReducer
import com.app.restaurantlogger.mvi.BaseViewModel
import com.app.restaurantlogger.mvi.FetchStatus
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: BaseViewModel<HomeState, HomeIntent>() {
    private val reducer = HomeReducer(HomeState.initial())

    override val state: StateFlow<HomeState>
        get() = reducer.state

    init {
        viewModelScope.launch {
            resetScreen()
        }
    }

    private fun sendEvent(event: HomeIntent) {
        reducer.sendEvent(event)
    }

    fun resetScreen() {
        sendEvent(HomeIntent.Initialize)
    }

    class HomeReducer(initialState: HomeState): BaseReducer<HomeState, HomeIntent>(initialState) {
        override fun reduce(oldState: HomeState, event: HomeIntent) {
            when(event) {
                is HomeIntent.Initialize -> {
                    setState(HomeState.initial())
                }
                is HomeIntent.UpdateData -> {
                    setState(oldState.copy(fetchStatus = FetchStatus.Fetched, homeData = event.data))
                }
            }
        }
    }
}