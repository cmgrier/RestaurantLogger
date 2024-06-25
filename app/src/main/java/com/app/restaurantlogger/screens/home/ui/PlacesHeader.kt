package com.app.restaurantlogger.screens.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.app.restaurantlogger.ui.TopBarHeader
import com.app.restaurantlogger.ui.theme.LocalEdgePadding

@Composable
fun PlacesHeader(
    modifier: Modifier,
    onSortClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
) {
    Row(modifier = modifier) {
        TopBarHeader(header = "Restaurants", modifier = Modifier.weight(1f).alignByBaseline())
        Text(
            modifier =
                Modifier
                    .alignByBaseline()
                    .clickable { onSortClick() },
            text = "Sort",
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Text(
            modifier =
                Modifier
                    .padding(start = 12.dp, end = LocalEdgePadding.current)
                    .alignByBaseline()
                    .clickable { onFilterClick() },
            text = "Filter",
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}
