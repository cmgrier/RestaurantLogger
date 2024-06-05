package com.app.restaurantlogger.home.mvi

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.dataModel.filter
import com.app.restaurantlogger.dataModel.sort
import com.app.restaurantlogger.home.ui.AddPlaceSheet
import com.app.restaurantlogger.home.ui.FilterSheet
import com.app.restaurantlogger.home.ui.HomeCard
import com.app.restaurantlogger.home.ui.PlacesHeader
import com.app.restaurantlogger.home.ui.SortSheet
import com.app.restaurantlogger.ui.enterZoomLeft
import com.app.restaurantlogger.ui.enterZoomRight
import com.app.restaurantlogger.ui.exitZoomLeft
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeScreen(
    homeViewModel: HomeViewModel,
    navController: NavHostController
) {
    composable(
        route = AppScreen.Home.name,
        enterTransition = {
            enterZoomLeft()
        },
        exitTransition = {
            exitZoomLeft()
        },
        popEnterTransition = {
            enterZoomRight()
        },
    ) {
        HomeScreen(
            homeViewModel = homeViewModel,
            navHostController = navController,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navHostController: NavHostController
) {
    val state by homeViewModel.state.collectAsState()

    val places = state.homeData.restaurants

    if (places.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalEdgePadding.current),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            stickyHeader {
                PlacesHeader(
                    onSortClick = { homeViewModel.showSortSheet() },
                    onFilterClick = { homeViewModel.showFilterSheet() },
                )
            }
            itemsIndexed(
                items = places.sort(state.homeData.sortMethod).filter(state.homeData.selectedFilters)
            ) { index, restaurant ->
                homeViewModel.populateReviews(restaurant.place)
                HomeCard(
                    modifier = Modifier,
                    index = index,
                    restaurant = restaurant,
                    onCardClick = {
                        navHostController.navigate(
                            route = "${AppScreen.Log.name}/${restaurant.place.uid}"
                        )
                    }
                )
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "No Restaurants Reviewed")
            Button(onClick = { homeViewModel.showAddPlaceSheet() }) {
                Text(text = "Add Restaurant")
            }
        }
    }

    AddPlaceSheet(
        modifier = Modifier,
        showSheet = state.homeData.showAddPlaceSheet,
        onDismissRequest = { homeViewModel.hideAddPlaceSheet() },
        onSubmitRequest = { homeViewModel.addPlace(it) },
    )

    SortSheet(
        modifier = Modifier,
        showSheet = state.homeData.showSortSheet,
        selectedMethod = state.homeData.sortMethod,
        onDismissRequest = { homeViewModel.hideSortSheet() },
        onSubmitRequest = { homeViewModel.changeSort(it) },
    )

    FilterSheet(
        modifier = Modifier,
        showSheet = state.homeData.showFilterSheet,
        selectedFilters = state.homeData.selectedFilters,
        onSubmitRequest = { homeViewModel.changeFilters(it) },
        onDismissRequest = { homeViewModel.hideFilterSheet() },
    )
}