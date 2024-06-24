package com.app.restaurantlogger.screens.log.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.database.Review
import com.app.restaurantlogger.database.sampleReview0
import com.app.restaurantlogger.ui.reviewstars.StarRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LogCard(
    modifier: Modifier = Modifier,
    review: Review = sampleReview0,
    onLongClick: (Review) -> Unit,
) {
    val isExpanded =
        remember {
            mutableStateOf(false)
        }
    Box(
        modifier =
            modifier
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium,
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.medium,
                )
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { if (review.details != null) isExpanded.value = !isExpanded.value },
                    onLongClick = { onLongClick(review) },
                ),
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
        Box(modifier = Modifier.fillMaxWidth()) {
            // Rating
            review.rating?.let {
                StarRow(
                    starCount = it,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier,
                )
            }
            // Date
            review.date?.let { Text(text = it) }
        }

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
