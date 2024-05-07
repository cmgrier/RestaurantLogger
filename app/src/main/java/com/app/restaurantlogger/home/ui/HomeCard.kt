package com.app.restaurantlogger.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.dataModel.Restaurant
import com.app.restaurantlogger.database.sampleReview0
import com.app.restaurantlogger.dataModel.sampleRestaurant
import com.app.restaurantlogger.database.Review
import com.app.restaurantlogger.ui.reviewstars.StarRow

@Preview
@Composable
fun HomeCard(
    modifier: Modifier = Modifier,
    index: Int = 0,
    restaurant: Restaurant = sampleRestaurant,
    onCardClick: () -> Unit = {}
) {
    val cardBackgroundColor = MaterialTheme.colorScheme.onPrimary
    val cardShape = MaterialTheme.shapes.large
    val cardBorderStroke = 4.dp
    val cardBorderColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = modifier
            .background(
                color = cardBackgroundColor,
                shape = cardShape
            )
            .border(
                width = cardBorderStroke,
                color = cardBorderColor,
                shape = cardShape
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onCardClick)
    ) {
        CardContent(restaurant = restaurant)
    }
}

@Composable
private fun CardContent(
    modifier: Modifier = Modifier,
    restaurant: Restaurant = sampleRestaurant
) {
    Column(modifier = modifier) {
        // Title
        val titleTextStyle = MaterialTheme.typography.headlineMedium
        val titleTextColor = MaterialTheme.colorScheme.onPrimaryContainer

        Text(
            text = restaurant.place.name,
            style = titleTextStyle,
            color = titleTextColor,
        )

        // Cuisine and Rating
        val cuisineTextStyle = MaterialTheme.typography.bodyLarge
        val cuisineTextColor = MaterialTheme.colorScheme.onSecondaryContainer
        val cuisineEndPadding = 8.dp

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            restaurant.place.cuisine?.let {
                Text(
                    text = it,
                    style = cuisineTextStyle,
                    color = cuisineTextColor,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(end = cuisineEndPadding)
                )
            }
            restaurant.averageRating()?.let { StarRow(starCount = it) }
        }

        // Recent Review
        if (restaurant.reviews.isNotEmpty()) {
            RecentReview(Modifier.padding(top = 8.dp), restaurant.reviews[0])
        }
    }
}

@Composable
private fun RecentReview(
    modifier: Modifier = Modifier,
    review: Review = sampleReview0,
) {
    val recentLogTitleTextStyle = MaterialTheme.typography.bodyMedium
    val recentLogTitleTextColor = MaterialTheme.colorScheme.onPrimaryContainer
    val recentLogDetailsTextStyle = MaterialTheme.typography.bodySmall
    val recentLogDetailsTextColor = MaterialTheme.colorScheme.secondary

    Column(modifier = modifier) {
        Text(
            text = review.headline,
            style = recentLogTitleTextStyle,
            color = recentLogTitleTextColor
        )
        review.details?.let {
            Text(
                text = it,
                style = recentLogDetailsTextStyle,
                color = recentLogDetailsTextColor,
                maxLines = 2
            )
        }
    }
}