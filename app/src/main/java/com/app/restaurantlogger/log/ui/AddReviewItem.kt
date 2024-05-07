package com.app.restaurantlogger.log.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddReviewItem(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = "+ Add Review",
        style = MaterialTheme.typography.displaySmall,
    )
}