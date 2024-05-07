package com.app.restaurantlogger.home.mvi

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.dataModel.Restaurant
import com.app.restaurantlogger.database.AppDatabase
import com.app.restaurantlogger.database.Place
import com.app.restaurantlogger.database.SimplePlace
import com.app.restaurantlogger.log.mvi.LogViewModel
import com.app.restaurantlogger.mvi.BaseReducer
import com.app.restaurantlogger.mvi.BaseViewModel
import com.app.restaurantlogger.mvi.FetchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appDatabase: AppDatabase,
): BaseViewModel<HomeState, HomeIntent>() {
    override val appScreen: AppScreen
        get() = AppScreen.Home

    private val reducer = HomeReducer(HomeState.initial())

    override val state: StateFlow<HomeState>
        get() = reducer.state

    override val floatingActionButtonAction: () -> Unit
        get() = { showSheet() }
    override val floatingActionButton: @Composable () -> Unit
        get() = {
            Text(
                text = "+ Add Place",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    override val topBar: @Composable () -> Unit
        get() = {}

    init {
        viewModelScope.launch {
            resetScreen()
        }
    }

    fun addPlace(
        restaurant: Restaurant,
    ) {
        addPlace(
            simplePlace = restaurant.place
        )
    }

    fun addPlace(
        simplePlace: SimplePlace,
    ) {
        val uid = simplePlace.hashCode()
        val newPlace = simplePlace.toPlace(uid)
        thread {
            sendEvent(HomeIntent.LoadingData)
            appDatabase.placeDao().insertAll(newPlace)
            resetScreen()
        }
    }

    fun showSheet() {
        sendEvent(HomeIntent.UpdateData(data = state.value.homeData.copy(showSheet = true)))
    }

    fun hideSheet() {
        sendEvent(HomeIntent.UpdateData(data = state.value.homeData.copy(showSheet = false)))
    }

    private fun sendEvent(event: HomeIntent) {
        reducer.sendEvent(event)
    }

    private fun resetScreen() {
        thread {
            sendEvent(HomeIntent.LoadingData)
            val restaurants = getAllRestaurants()
            sendEvent(HomeIntent.UpdateData(data = HomeData(places = restaurants, showSheet = false)))
        }
    }

    private fun getAllRestaurants(): List<Place> = appDatabase.placeDao().getAll()

    class HomeReducer(initialState: HomeState): BaseReducer<HomeState, HomeIntent>(initialState) {
        override fun reduce(oldState: HomeState, event: HomeIntent) {
            when(event) {
                is HomeIntent.LoadingData -> {
                    if (oldState.fetchStatus != FetchStatus.Fetching) {
                        setState(oldState.copy(fetchStatus = FetchStatus.Fetching))
                    }
                }
                is HomeIntent.UpdateData -> {
                    setState(oldState.copy(fetchStatus = FetchStatus.Fetched, homeData = event.data))
                }
            }
        }
    }
}