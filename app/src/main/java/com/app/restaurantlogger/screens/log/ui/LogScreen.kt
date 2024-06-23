package com.app.restaurantlogger.screens.log.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.screens.log.mvi.LogViewModel
import com.app.restaurantlogger.ui.enterZoomLeft
import com.app.restaurantlogger.ui.exitZoomLeft
import com.app.restaurantlogger.ui.exitZoomRight
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.google.accompanist.navigation.animation.composable

private const val PLACE_ID = "placeId"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.logScreen(logViewModel: LogViewModel) {
    composable(
        route = "${AppScreen.Log.name}/{placeId}",
        enterTransition = {
            enterZoomLeft()
        },
        exitTransition = {
            exitZoomLeft()
        },
        popExitTransition = {
            exitZoomRight()
        },
        arguments =
            listOf(
                navArgument(PLACE_ID) {
                    type = NavType.IntType
                },
            ),
    ) { backStackEntry ->
        backStackEntry.arguments?.getInt(PLACE_ID)?.let { placeId ->
            logViewModel.loadData(placeId = placeId)
        }
        LogScreen(viewModel = logViewModel)
    }
}

@Composable
fun LogScreen(viewModel: LogViewModel) {
    val state by viewModel.state.collectAsState()

    val restaurant = state.logData.restaurant

    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(horizontal = LocalEdgePadding.current),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val items = restaurant?.reviews.orEmpty()
        itemsIndexed(
            items = items,
        ) { index, item ->
            LogCard(review = item)
        }
        if (items.isEmpty()) {
            item {
                NoReviewsItem(
                    modifier =
                        Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                viewModel.showSheet()
                            },
                )
            }
        }
    }

    AddReviewSheet(
        modifier = Modifier,
        showSheet = state.showNewLogSheet,
        onDismissRequest = { viewModel.hideSheet() },
        onSubmitRequest = {
            viewModel.addReview(it)
            viewModel.hideSheet()
        },
    )
}
