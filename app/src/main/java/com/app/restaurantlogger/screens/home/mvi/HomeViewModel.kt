package com.app.restaurantlogger.screens.home.mvi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.dataModel.Restaurant
import com.app.restaurantlogger.database.AppDatabase
import com.app.restaurantlogger.database.Place
import com.app.restaurantlogger.database.SimplePlace
import com.app.restaurantlogger.database.toRestaurant
import com.app.restaurantlogger.mvi.BaseReducer
import com.app.restaurantlogger.mvi.BaseViewModel
import com.app.restaurantlogger.mvi.FetchStatus
import com.app.restaurantlogger.screens.home.ui.Filters
import com.app.restaurantlogger.screens.home.ui.PlacesHeader
import com.app.restaurantlogger.screens.home.ui.SortMethod
import com.app.restaurantlogger.ui.FloatingActionButtonContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val appDatabase: AppDatabase,
    ) : BaseViewModel<HomeState, HomeIntent>() {
        override val appScreen: AppScreen
            get() = AppScreen.Home

        private val reducer = HomeReducer(HomeState.initial())

        override val state: StateFlow<HomeState>
            get() = reducer.state

        override val data: HomeData
            get() = state.value.homeData

        override val floatingActionButtonAction: () -> Unit
            get() = { showPlaceSheet() }
        override val floatingActionButton: @Composable () -> Unit
            get() = {
                FloatingActionButtonContent(title = " + Add Place ")
            }
        override val topBarContent: @Composable (NavHostController, Modifier) -> Unit
            get() = { _, modifier ->
                PlacesHeader(
                    modifier = modifier,
                    onSortClick = { showSortSheet() },
                    onFilterClick = { showFilterSheet() },
                )
            }

        init {
            viewModelScope.launch {
                resetScreen()
            }
        }

        fun addPlace(restaurant: Restaurant) {
            addPlace(
                simplePlace = restaurant.place,
            )
        }

        fun updatePlace(place: Place) {
            thread {
                sendEvent(HomeIntent.LoadingData)
                appDatabase.placeDao().update(place = place)
                resetScreen()
            }
        }

        fun addPlace(simplePlace: SimplePlace) {
            val uid = simplePlace.hashCode()
            val newPlace = simplePlace.toPlace(uid)
            thread {
                sendEvent(HomeIntent.LoadingData)
                appDatabase.placeDao().insertAll(newPlace)
                resetScreen()
            }
        }

        fun showPlaceSheet() {
            sendEvent(HomeIntent.UpdateData(data = state.value.homeData.copy(showPlaceSheet = true)))
        }

        fun hideAddPlaceSheet() {
            sendEvent(
                HomeIntent.UpdateData(
                    data =
                        state.value.homeData.copy(
                            showPlaceSheet = false,
                        ),
                ),
            )
        }

        fun changeSort(sortMethod: SortMethod) {
            sendEvent(HomeIntent.UpdateData(data = state.value.homeData.copy(sortMethod = sortMethod)))
        }

        fun showSortSheet() {
            sendEvent(HomeIntent.UpdateData(data = state.value.homeData.copy(showSortSheet = true)))
        }

        fun hideSortSheet() {
            sendEvent(
                HomeIntent.UpdateData(
                    data =
                        state.value.homeData.copy(
                            showSortSheet = false,
                        ),
                ),
            )
        }

        fun changeFilters(newFilters: Filters) {
            sendEvent(HomeIntent.UpdateData(data = state.value.homeData.copy(selectedFilters = newFilters)))
        }

        fun showFilterSheet() {
            sendEvent(
                HomeIntent.UpdateData(
                    data =
                        state.value.homeData.copy(
                            showFilterSheet = true,
                        ),
                ),
            )
        }

        fun hideFilterSheet() {
            sendEvent(
                HomeIntent.UpdateData(
                    data =
                        state.value.homeData.copy(
                            showFilterSheet = false,
                        ),
                ),
            )
        }

        private fun sendEvent(event: HomeIntent) {
            reducer.sendEvent(event)
        }

        private fun resetScreen() {
            thread {
                sendEvent(HomeIntent.LoadingData)
                val places = getAllPlaces()
                sendEvent(
                    HomeIntent.UpdateData(
                        data =
                            HomeData.initial().copy(
                                restaurants = places.map { it.toRestaurant() },
                                sortMethod = state.value.homeData.sortMethod,
                            ),
                    ),
                )
            }
        }

        private fun getAllPlaces(): List<Place> = appDatabase.placeDao().getAll()

        fun populateReviews(place: Place) {
            thread {
                sendEvent(HomeIntent.LoadingData)
                val reviews = appDatabase.reviewDao().findByPlace(placeId = place.uid)
                val newRestaurants =
                    state.value.homeData.restaurants.map {
                        if (it.place.uid == place.uid) {
                            place.toRestaurant(reviews = reviews)
                        } else {
                            it
                        }
                    }
                sendEvent(HomeIntent.UpdateData(data = state.value.homeData.copy(restaurants = newRestaurants)))
            }
        }

        class HomeReducer(initialState: HomeState) : BaseReducer<HomeState, HomeIntent>(initialState) {
            override fun reduce(
                oldState: HomeState,
                event: HomeIntent,
            ) {
                when (event) {
                    is HomeIntent.LoadingData -> {
                        if (oldState.fetchStatus != FetchStatus.Fetching) {
                            setState(oldState.copy(fetchStatus = FetchStatus.Fetching))
                        }
                    }

                    is HomeIntent.UpdateData -> {
                        setState(
                            oldState.copy(
                                fetchStatus = FetchStatus.Fetched,
                                homeData = event.data,
                            ),
                        )
                    }
                }
            }
        }
    }
