package com.app.restaurantlogger.mvi

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.app.restaurantlogger.AppScreen
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<S: BaseState, in E: BaseIntent>: ViewModel() {
    abstract val appScreen: AppScreen
    abstract val state: Flow<S>
    abstract val floatingActionButtonAction: () -> Unit
    abstract val floatingActionButton: @Composable () -> Unit
    abstract val topBar: @Composable () -> Unit
}