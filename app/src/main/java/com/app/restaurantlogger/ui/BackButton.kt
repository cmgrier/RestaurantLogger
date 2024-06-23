package com.app.restaurantlogger.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.restaurantlogger.R

@Composable
fun BackButton(navHostController: NavHostController) {
    IconButton(
        onClick = { navHostController.popBackStack() },
    ) {
        Icon(
            modifier = Modifier.size(36.dp),
            painter = painterResource(id = R.drawable.round_arrow_back_24),
            contentDescription = "back",
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
