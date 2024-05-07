package com.app.restaurantlogger.log.mvi

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.R
import com.app.restaurantlogger.log.ui.AddReviewItem
import com.app.restaurantlogger.log.ui.AddReviewSheet
import com.app.restaurantlogger.log.ui.LogCard
import com.app.restaurantlogger.ui.enterZoomLeft
import com.app.restaurantlogger.ui.exitZoomLeft
import com.app.restaurantlogger.ui.exitZoomRight
import com.google.accompanist.navigation.animation.composable

private const val PLACE_ID = "placeId"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.logScreen(
    logViewModel: LogViewModel,
    navController: NavHostController,
) {
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
        arguments = listOf(
            navArgument(PLACE_ID) {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->
        backStackEntry.arguments?.getInt(PLACE_ID)?.let { placeId ->
            logViewModel.loadData(placeId = placeId)
        }
        LogScreen(viewModel = logViewModel, navHostController = navController)
    }
}

@Composable
fun LogScreen(
    viewModel: LogViewModel,
    navHostController: NavHostController
) {
    val state by viewModel.state.collectAsState()

    val restaurant = state.logData.restaurant

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { navHostController.popBackStack() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_arrow_back_24),
                    contentDescription = "back"
                )
            }
            val titleTextStyle = MaterialTheme.typography.headlineMedium
            val titleTextColor = MaterialTheme.colorScheme.onPrimaryContainer

            Text(
                text = restaurant?.place?.name.orEmpty(),
                style = titleTextStyle,
                color = titleTextColor,
            )
        }

        val showSheet = remember {
            mutableStateOf(false)
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(
                items = restaurant?.reviews.orEmpty()
            ) {index, item ->
                LogCard(review = item)
            }
            item {
                AddReviewItem(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            showSheet.value = true
                        }
                )
            }
        }

        AddReviewSheet(
            modifier = Modifier,
            showSheet = showSheet.value,
            onDismissRequest = { showSheet.value = false },
            onSubmitRequest = {
                viewModel.addReview(it)
                showSheet.value = false
            }
        )
    }
}
