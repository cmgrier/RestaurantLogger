package com.app.restaurantlogger.log.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NoReviewsItem(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "No Reviews Found",
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = "+ Add Review",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}