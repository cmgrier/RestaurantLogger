package com.app.restaurantlogger.ui.reviewstars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.app.restaurantlogger.R
import com.app.restaurantlogger.util.IconSize

@Composable
fun StarRow(
    modifier: Modifier = Modifier,
    starCount: Float,
    starSize: IconSize = IconSize.Medium,
) {
    val starIconFull = painterResource(id = R.drawable.round_star_24)
    val starIconHalf = painterResource(id = R.drawable.round_star_half_24)
    val starIconEmpty = painterResource(id = R.drawable.round_star_border_24)

    val filledStars = starCount.toInt() + if (starCount.mod(1f) > .75f) 1 else 0
    Row(modifier = modifier) {
        repeat(filledStars) {
            StarIcon(
                painter = starIconFull,
                contentDescription = "full review star",
                starSize = starSize,
            )
        }
        val hasHalfStar = starCount - filledStars.toFloat() > 0.25f
        if (hasHalfStar) {
            StarIcon(
                painter = starIconHalf,
                contentDescription = "half review star",
                starSize = starSize,
            )
        }
        val emptyStars = 5 - filledStars - if (hasHalfStar) 1 else 0
        repeat(emptyStars) {
            StarIcon(
                painter = starIconEmpty,
                contentDescription = "empty review star",
                starSize = starSize,
            )
        }
    }
}

@Composable
private fun StarIcon(
    painter: Painter,
    contentDescription: String,
    starSize: IconSize,
    modifier: Modifier = Modifier,
) {
    val starColor = MaterialTheme.colorScheme.primary

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        tint = starColor,
        modifier = modifier.size(starSize.size),
    )
}
