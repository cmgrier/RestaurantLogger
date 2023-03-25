package com.app.restaurantlogger.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<S: BaseState, in E: BaseIntent>: ViewModel() {
    abstract val state: Flow<S>
}