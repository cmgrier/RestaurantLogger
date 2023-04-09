package com.app.restaurantlogger.log.mvi

import androidx.lifecycle.viewModelScope
import com.app.restaurantlogger.mvi.BaseReducer
import com.app.restaurantlogger.mvi.BaseViewModel
import com.app.restaurantlogger.mvi.FetchStatus
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogViewModel : BaseViewModel<LogState, LogIntent>() {
    private val reducer = LogReducer(LogState.initial())

    override val state: StateFlow<LogState>
        get() = reducer.state

    init {
        viewModelScope.launch {
            resetScreen()
        }
    }

    private fun sendEvent(event: LogIntent) {
        reducer.sendEvent(event)
    }

    fun resetScreen() {
        sendEvent(LogIntent.Initialize)
    }

    class LogReducer(initialState: LogState): BaseReducer<LogState, LogIntent>(initialState) {
        override fun reduce(oldState: LogState, event: LogIntent) {
            when(event) {
                is LogIntent.Initialize -> {
                    setState(LogState.initial())
                }
                is LogIntent.UpdateData -> {
                    setState(oldState.copy(fetchStatus = FetchStatus.Fetched, logData = event.data))
                }
            }
        }
    }
}