package com.app.restaurantlogger.mvi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.app.restaurantlogger.AppScreen
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<S : BaseState, in E : BaseIntent> : ViewModel() {
    abstract val appScreen: AppScreen
    abstract val state: Flow<S>
    abstract val data: BaseData
    abstract val floatingActionButtonAction: () -> Unit
    abstract val floatingActionButton: @Composable () -> Unit
    abstract val topBarContent: @Composable (NavHostController, Modifier) -> Unit
}
