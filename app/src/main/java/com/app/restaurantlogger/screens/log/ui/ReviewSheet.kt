package com.app.restaurantlogger.screens.log.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
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
import com.app.restaurantlogger.database.Review
import com.app.restaurantlogger.database.SimpleReview
import com.app.restaurantlogger.database.nearestHalfRating
import com.app.restaurantlogger.ui.PrimaryButton
import com.app.restaurantlogger.ui.StyledTextField
import com.app.restaurantlogger.ui.TextButton
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.app.restaurantlogger.ui.theme.LocalSheetBottomPadding
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewSheet(
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    onSubmitRequest: (SimpleReview) -> Unit,
    modifier: Modifier = Modifier,
    initialReview: Review? = null,
) {
    if (showSheet) {
        val sheetState = rememberModalBottomSheetState()
        var headline by remember {
            mutableStateOf(initialReview?.headline.orEmpty())
        }
        var details by remember {
            mutableStateOf(initialReview?.details)
        }
        var rating by remember {
            mutableStateOf(initialReview?.rating)
        }

        var date by remember {
            mutableStateOf(initialReview?.date)
        }

        val review =
            DataReview(
                placeId = -1,
                rating = rating,
                headline = headline,
                details = details,
                date = date,
            )
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
                val currentDate = convertMillisToDate(System.currentTimeMillis())

                val dateDisplayText =
                    date?.let {
                        it.ifEmpty { currentDate }
                    } ?: currentDate

                var isDatePickerVisible by remember {
                    mutableStateOf(false)
                }

                TextButton(
                    modifier = Modifier.align(Alignment.End),
                    text = dateDisplayText,
                    onClick = { isDatePickerVisible = true },
                )

                if (isDatePickerVisible) {
                    ReviewDatePicker(
                        onSubmitRequest = { newDate ->
                            date = newDate
                        },
                        onDismissRequest = {
                            isDatePickerVisible = false
                        },
                    )
                }

                StyledTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = headline,
                    placeholder = "Headline",
                    onValueChange = {
                        headline = it
                    },
                    textStyle = MaterialTheme.typography.headlineSmall,
                )

                StyledTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = details.orEmpty(),
                    placeholder = "Details",
                    onValueChange = {
                        details = it
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                )

                RatingSelector(
                    modifier = Modifier.padding(vertical = 16.dp),
                    rating = rating ?: 0f,
                    onValueChange = { rating = nearestHalfRating(it) },
                )

                PrimaryButton(
                    isEnabled = review.isValid(),
                    text = "Submit",
                    onClick = {
                        onSubmitRequest(review)
                        onDismissRequest()
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReviewDatePicker(
    onSubmitRequest: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val datePickerState =
        rememberDatePickerState(
            selectableDates =
                object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        return utcTimeMillis <= System.currentTimeMillis()
                    }
                },
        )

    val selectedDate =
        datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: ""

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            PrimaryButton(
                text = "Submit",
                onClick = { onSubmitRequest(selectedDate) },
            )
        },
        dismissButton = {
            TextButton(text = "Dismiss", onClick = onDismissRequest)
        },
    ) {
        DatePicker(state = rememberDatePickerState())
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}

private fun SimpleReview.isValid(): Boolean = this.headline.isNotEmpty()
