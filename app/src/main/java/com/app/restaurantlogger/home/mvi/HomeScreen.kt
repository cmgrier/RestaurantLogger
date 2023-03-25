package com.app.restaurantlogger.home.mvi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.home.ui.HomeCard

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsState()

    val cardPadding = 6.dp

    LazyColumn {
        itemsIndexed(
            items = state.homeData.restaurants
        ) { index, item ->
            HomeCard(
                modifier = Modifier.padding(cardPadding),
                index = index,
                restaurant = item
            )
        }
    }
}