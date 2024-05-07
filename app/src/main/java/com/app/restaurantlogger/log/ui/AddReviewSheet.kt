package com.app.restaurantlogger.log.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.database.DataReview
import com.app.restaurantlogger.database.SimpleReview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewSheet(
    modifier: Modifier,
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    onSubmitRequest: (SimpleReview) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val headline = remember {
        mutableStateOf("")
    }
    val details = remember {
        mutableStateOf<String?>(null)
    }
    val rating = remember {
        mutableStateOf<Float?>(null)
    }
    val review = DataReview(placeId = -1, rating = rating.value, headline = headline.value, details = details.value)

    if (showSheet) {
        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = headline.value,
                    placeholder = {
                        Text(text = "Headline")
                    },
                    onValueChange = {
                        headline.value = it
                    },
                )

                TextField(
                    value = details.value.orEmpty(),
                    placeholder = {
                        Text(text = "Details")
                    },
                    onValueChange = {
                        details.value = it
                    },
                )

                Slider(
                    value = rating.value ?: 0f,
                    onValueChange = { rating.value = it },
                    valueRange = 0f..5f,
                )

                Button(
                    enabled = review.isValid(),
                    onClick = { onSubmitRequest(review) }
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

@Composable
private fun RatingRow(modifier: Modifier) {
}

private fun SimpleReview.isValid(): Boolean = this.headline.isNotEmpty()
