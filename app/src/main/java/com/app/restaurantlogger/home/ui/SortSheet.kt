package com.app.restaurantlogger.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.R
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.app.restaurantlogger.ui.theme.LocalSheetBottomPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortSheet(
    modifier: Modifier,
    showSheet: Boolean,
    selectedMethod: SortMethod,
    onSubmitRequest: (SortMethod) -> Unit,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LocalEdgePadding.current)
                    .padding(bottom = LocalSheetBottomPadding.current),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (method in SortMethod.entries) {
                    SelectableRow(text = method.name, isSelected = selectedMethod == method) {
                        onSubmitRequest(method)
                        onDismissRequest()
                    }
                }
            }
        }
    }
}

enum class SortMethod {
    Rating,
    Name,
}

@Composable
fun SelectableRow(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.checkmark_24),
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
