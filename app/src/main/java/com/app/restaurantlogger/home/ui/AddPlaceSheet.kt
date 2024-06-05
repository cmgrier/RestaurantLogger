package com.app.restaurantlogger.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.database.DataPlace
import com.app.restaurantlogger.database.SimplePlace
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.app.restaurantlogger.ui.theme.LocalSheetBottomPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceSheet(
    modifier: Modifier,
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    onSubmitRequest: (SimplePlace) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    if (showSheet) {
        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LocalEdgePadding.current)
                    .padding(bottom = LocalSheetBottomPadding.current),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val name = remember { mutableStateOf("") }
                StyledTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    placeholder = "Name",
                )

                val cuisine = remember { mutableStateOf("") }
                StyledTextField(
                    value = cuisine.value,
                    onValueChange = { cuisine.value = it },
                    placeholder = "Cuisine",
                )

                val address = remember { mutableStateOf("") }
                StyledTextField(
                    value = address.value,
                    onValueChange = { address.value = it },
                    placeholder = "Google Maps Link",
                )

                val place = DataPlace(
                    name = name.value,
                    address = address.value,
                    cuisine = cuisine.value,
                )
                Button(
                    enabled = place.isValid(),
                    onClick = {
                        onSubmitRequest(place)
                        onDismissRequest()
                    }
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
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            placeholder?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        colors = TextFieldDefaults.colors().copy(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        )
    )
}

fun DataPlace.isValid(): Boolean = this.name.isNotEmpty()