package com.app.restaurantlogger.ui.reviewstars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.R

@Composable
fun StarRow(
    modifier: Modifier = Modifier,
    starCount: Float
) {
    val starIconFull = painterResource(id = R.drawable.round_star_24)
    val starIconHalf = painterResource(id = R.drawable.round_star_half_24)
    val starIconEmpty = painterResource(id = R.drawable.round_star_border_24)

    val filledStars = starCount.toInt()
    Row(modifier = modifier) {
        repeat(filledStars) {
            StarIcon(painter = starIconFull, contentDescription = "full review star")
        }
        val hasHalfStar = starCount - filledStars > 0
        if (hasHalfStar) {
            StarIcon(painter = starIconHalf, contentDescription = "half review star")
        }
        val emptyStars = 5 - filledStars - if (hasHalfStar) 1 else 0
        repeat(emptyStars) {
            StarIcon(painter = starIconEmpty, contentDescription = "empty review star")
        }
    }
}

@Composable
private fun StarIcon(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val starColor = MaterialTheme.colorScheme.primary

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        tint = starColor,
        modifier = modifier.size(22.dp)
    )
}