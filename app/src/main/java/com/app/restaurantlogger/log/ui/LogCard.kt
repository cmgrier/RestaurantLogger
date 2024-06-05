package com.app.restaurantlogger.log.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.app.restaurantlogger.database.sampleReview0
import com.app.restaurantlogger.database.Review
import com.app.restaurantlogger.ui.reviewstars.StarRow

@Composable
fun LogCard(
    modifier: Modifier = Modifier,
    review: Review = sampleReview0,
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = review.details != null,
            ) { isExpanded.value = !isExpanded.value }
    ) {
        CardContent(
            review = review,
            isExpanded = isExpanded.value,
        )
    }
}

@Composable
private fun CardContent(
    review: Review,
    isExpanded: Boolean,
) {
    Column {
        // Rating
        review.rating?.let { StarRow(starCount = it) }

        // Headline
        val headlineTextStyle = MaterialTheme.typography.headlineMedium
        val headlineTextColor = MaterialTheme.colorScheme.onPrimaryContainer

        Text(
            text = review.headline,
            color = headlineTextColor,
            style = headlineTextStyle,
        )

        // Details
        if (review.details != null) {
            val detailsTextStyle = MaterialTheme.typography.bodyLarge
            val detailsTextColor = MaterialTheme.colorScheme.onSecondaryContainer
            Text(
                text = review.details,
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis,
                color = detailsTextColor,
                style = detailsTextStyle,
            )
        }
    }
}