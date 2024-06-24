package com.app.restaurantlogger.screens.log.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.restaurantlogger.ui.TextButton

@Composable
fun NoReviewsItem(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "No Reviews Found",
            style = MaterialTheme.typography.headlineSmall,
        )
        TextButton(text = "+ Add Review", onClick = onClick)
    }
}
