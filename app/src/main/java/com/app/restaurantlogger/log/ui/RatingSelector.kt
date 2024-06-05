package com.app.restaurantlogger.log.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.app.restaurantlogger.ui.reviewstars.StarRow
import com.app.restaurantlogger.ui.reviewstars.StarSize

@Composable
fun RatingSelector(
    rating: Float?,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.wrapContentWidth(), contentAlignment = Alignment.Center) {
        Slider(
            modifier =
                Modifier
                    .matchParentSize()
                    .alpha(0f),
            value = rating ?: 0f,
            onValueChange = { onValueChange(it) },
            valueRange = 0f..5f,
        )

        StarRow(
            starCount = rating ?: 0f,
            starSize = StarSize.XLarge,
        )
    }
}
