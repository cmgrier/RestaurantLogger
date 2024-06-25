package com.app.restaurantlogger.screens.log.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.database.Review
import com.app.restaurantlogger.screens.log.mvi.LogViewModel
import com.app.restaurantlogger.ui.enterShimmer
import com.app.restaurantlogger.ui.exitShimmer
import com.app.restaurantlogger.ui.exitSlideLeft
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.google.accompanist.navigation.animation.composable

private const val PLACE_ID = "placeId"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.logScreen(logViewModel: LogViewModel) {
    composable(
        route = "${AppScreen.Log.name}/{placeId}",
        enterTransition = {
            enterShimmer()
        },
        exitTransition = {
            exitShimmer() + exitSlideLeft()
        },
        popExitTransition = {
            exitShimmer()
        },
        popEnterTransition = {
            enterShimmer()
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

    var selectedReview by remember {
        mutableStateOf<Review?>(null)
    }

    val items = restaurant?.reviews.orEmpty()

    if (items.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = LocalEdgePadding.current),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            itemsIndexed(
                items = items,
            ) { index, item ->
                LogCard(
                    review = item,
                    onLongClick = { review ->
                        selectedReview = review
                        viewModel.showSheet()
                    },
                )
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "No Reviews Yet...")
            Button(onClick = { viewModel.showSheet() }) {
                Text(text = "+ Add Review")
            }
        }
    }

    ReviewSheet(
        modifier = Modifier,
        showSheet = state.showLogSheet,
        onDismissRequest = {
            viewModel.hideSheet()
            selectedReview = null
        },
        onSubmitRequest = { newReview ->
            selectedReview?.let { oldReview ->
                viewModel.updateReview(
                    review =
                        oldReview.copy(
                            placeId = newReview.placeId,
                            rating = newReview.rating,
                            headline = newReview.headline,
                            details = newReview.details,
                        ),
                )
            } ?: run {
                viewModel.addReview(newReview)
            }
        },
        initialReview = selectedReview,
    )
}
