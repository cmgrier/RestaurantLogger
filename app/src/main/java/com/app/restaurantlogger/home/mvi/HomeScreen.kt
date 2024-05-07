package com.app.restaurantlogger.home.mvi

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.dataModel.sampleRestaurant
import com.app.restaurantlogger.database.toRestaurant
import com.app.restaurantlogger.home.ui.AddPlaceSheet
import com.app.restaurantlogger.home.ui.HomeCard
import com.app.restaurantlogger.log.mvi.LogViewModel
import com.app.restaurantlogger.ui.enterZoomLeft
import com.app.restaurantlogger.ui.enterZoomRight
import com.app.restaurantlogger.ui.exitZoomLeft
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

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navHostController: NavHostController
) {
    val state by homeViewModel.state.collectAsState()

    val cardPadding = 6.dp
    val places = state.homeData.places

    if (places.isNotEmpty()) {
        LazyColumn {
            itemsIndexed(
                items = places
            ) { index, place ->
                HomeCard(
                    modifier = Modifier.padding(cardPadding),
                    index = index,
                    restaurant = place.toRestaurant(),
                    onCardClick = {
                        navHostController.navigate(
                            route = "${AppScreen.Log.name}/${place.uid}"
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
            Button(onClick = { homeViewModel.addPlace(sampleRestaurant) }) {
                Text(text = "Add Restaurant")
            }
        }
    }

    AddPlaceSheet(
        modifier = Modifier,
        showSheet = state.homeData.showSheet,
        onDismissRequest = { homeViewModel.hideSheet() },
        onSubmitRequest = {

        },
    )
}