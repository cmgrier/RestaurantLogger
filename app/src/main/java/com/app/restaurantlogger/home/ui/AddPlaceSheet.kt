package com.app.restaurantlogger.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.database.DataPlace
import com.app.restaurantlogger.database.SimplePlace

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
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val name = remember { mutableStateOf("") }
                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    placeholder = {
                        Text(text = "Name")
                    }
                )

                val cuisine = remember { mutableStateOf("") }
                TextField(
                    value = cuisine.value,
                    onValueChange = { cuisine.value = it },
                    placeholder = {
                        Text(text = "Cuisine")
                    }
                )

                val address = remember { mutableStateOf("") }
                TextField(
                    value = address.value,
                    onValueChange = { address.value = it },
                    placeholder = {
                        Text(text = "Address")
                    }
                )

                val place =
                    DataPlace(name = name.value, address = address.value, cuisine = cuisine.value)
                Button(
                    enabled = place.isValid(),
                    onClick = { onSubmitRequest(place) }
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

fun DataPlace.isValid(): Boolean = this.name.isNotEmpty()