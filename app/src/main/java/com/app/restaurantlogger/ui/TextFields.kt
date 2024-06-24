package com.app.restaurantlogger.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    TextField(
        modifier = modifier,
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
        textStyle = textStyle,
        colors =
            TextFieldDefaults.colors().copy(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ),
    )
}
