package com.app.restaurantlogger.log.mvi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.app.restaurantlogger.R
import com.app.restaurantlogger.log.ui.LogCard

@Composable
fun LogScreen(
    viewModel: LogViewModel,
    navHostController: NavHostController
) {
    val state by viewModel.state.collectAsState()

    Column {
        Row {
            IconButton(
                onClick = { navHostController.popBackStack() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_arrow_back_24),
                    contentDescription = "back"
                )
            }
        }

        LazyColumn {
            itemsIndexed(
                items = state.logData.restaurant.logs
            ) {index, item ->
                LogCard(mealLog = item)
            }
        }
    }
}
