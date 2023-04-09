package com.app.restaurantlogger.log.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.restaurantlogger.dataModel.MealLog
import com.app.restaurantlogger.dataModel.sampleMealLog0
import com.app.restaurantlogger.ui.reviewstars.StarRow

@Composable
fun LogCard(
    modifier: Modifier = Modifier,
    mealLog: MealLog = sampleMealLog0
) {
    Box(
        modifier = modifier
    ) {
        CardContent(
            mealLog = mealLog
        )
    }
}

@Composable
private fun CardContent(
    mealLog: MealLog
) {
    Column {
        // Rating
        StarRow(starCount = mealLog.rating)

        // Headline
        val headlineTextStyle = MaterialTheme.typography.headlineMedium
        val headlineTextColor = MaterialTheme.colorScheme.onPrimaryContainer

        Text(
            text = mealLog.headline,
            color = headlineTextColor,
            style = headlineTextStyle
        )

        //
    }
}