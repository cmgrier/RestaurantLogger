package com.app.restaurantlogger.screens.home.ui

import androidx.compose.foundation.layout.Arrangement
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
import com.app.restaurantlogger.database.DataPlace
import com.app.restaurantlogger.database.Place
import com.app.restaurantlogger.database.SimplePlace
import com.app.restaurantlogger.ui.StyledTextField
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.app.restaurantlogger.ui.theme.LocalSheetBottomPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceSheet(
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    onSubmitRequest: (SimplePlace) -> Unit,
    modifier: Modifier = Modifier,
    initialPlace: Place? = null,
) {
    if (showSheet) {
        val sheetState = rememberModalBottomSheetState()
        var name by remember { mutableStateOf(initialPlace?.name.orEmpty()) }
        var cuisine by remember { mutableStateOf(initialPlace?.cuisine.orEmpty()) }
        var address by remember { mutableStateOf(initialPlace?.address.orEmpty()) }
        val place = DataPlace(name = name, cuisine = cuisine, address = address)

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
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                StyledTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Name",
                    textStyle = MaterialTheme.typography.headlineSmall,
                )

                StyledTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = cuisine,
                    onValueChange = { cuisine = it },
                    placeholder = "Cuisine",
                )

                StyledTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = address,
                    onValueChange = { address = it },
                    placeholder = "Google Maps Link",
                )

                Button(
                    enabled = place.isValid(),
                    onClick = {
                        onSubmitRequest(place)
                        onDismissRequest()
                    },
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

fun SimplePlace.isValid(): Boolean = this.name.isNotEmpty()
