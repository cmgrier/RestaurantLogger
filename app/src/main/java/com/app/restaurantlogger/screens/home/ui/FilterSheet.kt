package com.app.restaurantlogger.screens.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.dataModel.Cuisine
import com.app.restaurantlogger.database.nearestHalfRating
import com.app.restaurantlogger.screens.log.ui.RatingSelector
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.app.restaurantlogger.ui.theme.LocalSheetBottomPadding

private const val UNSELECTED_TEXT = "Unselected"

data class Filters(
    val cuisine: Cuisine? = null,
    val ratingLowerLimit: Float,
    val ratingUpperLimit: Float,
    val reviewed: Boolean? = null,
) {
    companion object {
        val default =
            Filters(
                cuisine = null,
                ratingLowerLimit = 0f,
                ratingUpperLimit = 5f,
                reviewed = null,
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(
    modifier: Modifier,
    showSheet: Boolean,
    selectedFilters: Filters,
    onSubmitRequest: (Filters) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    if (showSheet) {
        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismissRequest = { onDismissRequest() },
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = LocalEdgePadding.current)
                        .padding(bottom = LocalSheetBottomPadding.current),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                var currentFilters by remember { mutableStateOf(selectedFilters) }

                // Cuisine Filter
                var selectedCuisineText by remember {
                    mutableStateOf(
                        selectedFilters.cuisine?.name ?: UNSELECTED_TEXT,
                    )
                }

                CuisineFilter(selectedCuisineText = selectedCuisineText) { cuisine ->
                    selectedCuisineText = cuisine?.name ?: UNSELECTED_TEXT
                    currentFilters = currentFilters.copy(cuisine = cuisine)
                }

                // Rating Filter
                var maxRating by remember { mutableFloatStateOf(5f) }
                var minRating by remember { mutableFloatStateOf(0f) }

                RatingFilter(
                    minRating = minRating,
                    maxRating = maxRating,
                    onMinRatingChange = {
                        minRating = nearestHalfRating(it)
                        currentFilters = currentFilters.copy(ratingLowerLimit = minRating)
                    },
                    onMaxRatingChange = {
                        maxRating = nearestHalfRating(it)
                        currentFilters = currentFilters.copy(ratingUpperLimit = maxRating)
                    },
                )

                BottomActions(
                    selectedFilters = selectedFilters,
                    currentFilters = currentFilters,
                    onSubmitRequest = onSubmitRequest,
                )
            }
        }
    }
}

@Composable
private fun BottomActions(
    selectedFilters: Filters,
    currentFilters: Filters,
    onSubmitRequest: (Filters) -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = LocalEdgePadding.current),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedButton(
            enabled = currentFilters != Filters.default,
            onClick = { onSubmitRequest(Filters.default) },
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Reset",
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Button(
            enabled = currentFilters != selectedFilters,
            onClick = { onSubmitRequest(currentFilters) },
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Submit",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CuisineFilter(
    modifier: Modifier = Modifier,
    selectedCuisineText: String,
    onItemClick: (Cuisine?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
    ) {
        Text(
            text = "Cuisine",
            style = MaterialTheme.typography.labelLarge,
        )
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                value = selectedCuisineText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier =
                    Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                Cuisine.entries.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name) },
                        onClick = {
                            onItemClick(item)
                            expanded = false
                        },
                    )
                }
                DropdownMenuItem(
                    text = { Text(text = UNSELECTED_TEXT) },
                    onClick = {
                        onItemClick(null)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
private fun RatingFilter(
    minRating: Float,
    maxRating: Float,
    onMinRatingChange: (Float) -> Unit,
    onMaxRatingChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(text = "Max: $maxRating")
        RatingSelector(
            rating = maxRating,
            onValueChange = { onMaxRatingChange(it) },
        )

        Text(text = "Min: $minRating")
        RatingSelector(
            rating = minRating,
            onValueChange = { onMinRatingChange(it) },
        )
    }
}
