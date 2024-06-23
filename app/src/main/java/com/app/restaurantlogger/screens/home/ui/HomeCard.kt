package com.app.restaurantlogger.screens.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.dataModel.Restaurant
import com.app.restaurantlogger.dataModel.sampleRestaurant
import com.app.restaurantlogger.database.Review
import com.app.restaurantlogger.database.sampleReview0
import com.app.restaurantlogger.database.sampleReviewList
import com.app.restaurantlogger.ui.reviewstars.StarRow
import com.app.restaurantlogger.util.IconSize
import kotlinx.coroutines.delay

@Preview
@Composable
fun HomeCard(
    modifier: Modifier = Modifier,
    index: Int = 0,
    restaurant: Restaurant = sampleRestaurant,
    onCardClick: () -> Unit = {},
) {
    val cardBackgroundColor = MaterialTheme.colorScheme.primaryContainer
    val cardShape = MaterialTheme.shapes.large
    val cardBorderStroke = 2.dp
    val cardBorderColor = MaterialTheme.colorScheme.primary

    Box(
        modifier =
            modifier
                .clickable(onClick = onCardClick)
                .background(
                    color = cardBackgroundColor,
                    shape = cardShape,
                )
                .border(
                    width = cardBorderStroke,
                    color = cardBorderColor,
                    shape = cardShape,
                )
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp, horizontal = 16.dp),
    ) {
        CardContent(restaurant = restaurant)
    }
}

@Composable
private fun CardContent(
    modifier: Modifier = Modifier,
    restaurant: Restaurant = sampleRestaurant,
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
        val cuisineTextColor = MaterialTheme.colorScheme.secondary
        val cuisineEndPadding = 8.dp

        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            restaurant.place.cuisine?.let {
                Text(
                    text = it,
                    style = cuisineTextStyle,
                    color = cuisineTextColor,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(end = cuisineEndPadding),
                )
            }
            restaurant.averageRating()?.let { StarRow(starCount = it) }
        }

        // Reviews
        if (restaurant.reviews.isNotEmpty()) {
            Review(Modifier.padding(top = 8.dp), restaurant.reviews)
        }
    }
}

@Composable
private fun getCurrentReview(reviews: List<Review>): Review {
    val currentIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            currentIndex.intValue = (currentIndex.intValue + 1) % reviews.size
            delay(5000)
        }
    }

    return reviews[currentIndex.intValue]
}

@Composable
private fun Review(
    modifier: Modifier = Modifier,
    reviews: List<Review> = sampleReviewList,
) {
    val recentLogTitleTextStyle = MaterialTheme.typography.bodyMedium
    val recentLogTitleTextColor = MaterialTheme.colorScheme.onPrimaryContainer
    val recentLogDetailsTextStyle = MaterialTheme.typography.bodySmall
    val recentLogDetailsTextColor = MaterialTheme.colorScheme.secondary

    val review = getCurrentReview(reviews = reviews)

    var composableReview by remember {
        mutableStateOf(sampleReview0)
    }

    var contentVisible by remember {
        mutableStateOf(true)
    }

    val fadeDuration = 300

    LaunchedEffect(
        key1 = review,
    ) {
        if (composableReview == sampleReview0) {
            composableReview = review
        } else {
            contentVisible = false
            delay(fadeDuration.toLong())
            composableReview = review
            contentVisible = true
        }
    }

    AnimatedVisibility(
        visible = contentVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = fadeDuration)),
        exit = fadeOut(animationSpec = tween(durationMillis = fadeDuration)),
    ) {
        Column(modifier = modifier) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = composableReview.headline,
                    style = recentLogTitleTextStyle,
                    color = recentLogTitleTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                composableReview.rating?.let { StarRow(starCount = it, starSize = IconSize.Small) }
            }
            composableReview.details?.let {
                Text(
                    text = it,
                    style = recentLogDetailsTextStyle,
                    color = recentLogDetailsTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
