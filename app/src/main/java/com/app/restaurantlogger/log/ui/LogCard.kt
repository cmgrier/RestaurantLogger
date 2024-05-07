package com.app.restaurantlogger.log.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.restaurantlogger.database.sampleReview0
import com.app.restaurantlogger.database.Review
import com.app.restaurantlogger.ui.reviewstars.StarRow

@Composable
fun LogCard(
    modifier: Modifier = Modifier,
    review: Review = sampleReview0,
) {
    Box(
        modifier = modifier
    ) {
        CardContent(
            review = review,
        )
    }
}

@Composable
private fun CardContent(
    review: Review
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
            style = headlineTextStyle
        )

        //
    }
}