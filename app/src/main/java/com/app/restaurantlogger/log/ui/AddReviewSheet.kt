package com.app.restaurantlogger.log.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.database.DataReview
import com.app.restaurantlogger.database.SimpleReview
import com.app.restaurantlogger.database.nearestHalfRating
import com.app.restaurantlogger.home.ui.StyledTextField
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.app.restaurantlogger.ui.theme.LocalSheetBottomPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewSheet(
    modifier: Modifier,
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    onSubmitRequest: (SimpleReview) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var headline by remember {
        mutableStateOf("")
    }
    var details by remember {
        mutableStateOf<String?>(null)
    }
    var rating by remember {
        mutableStateOf<Float?>(null)
    }

    val starRating = nearestHalfRating(rating ?: 0f)
    val review = DataReview(placeId = -1, rating = rating, headline = headline, details = details)

    if (showSheet) {
        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = LocalEdgePadding.current)
                        .padding(bottom = LocalSheetBottomPadding.current),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                StyledTextField(
                    value = headline,
                    placeholder = "Headline",
                    onValueChange = {
                        headline = it
                    },
                )

                StyledTextField(
                    value = details.orEmpty(),
                    placeholder = "Details",
                    onValueChange = {
                        details = it
                    },
                )

                RatingSelector(
                    modifier = Modifier.padding(vertical = 16.dp),
                    rating = rating ?: 0f,
                    onValueChange = { rating = nearestHalfRating(it) },
                )

                Button(
                    enabled = review.isValid(),
                    onClick = { onSubmitRequest(review) },
                ) {
                    Text(
                        text = "Submit",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

private fun SimpleReview.isValid(): Boolean = this.headline.isNotEmpty()
