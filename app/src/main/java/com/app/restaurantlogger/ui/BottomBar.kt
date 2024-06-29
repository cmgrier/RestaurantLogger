package com.app.restaurantlogger.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.mvi.BaseViewModel
import com.app.restaurantlogger.util.IconSize

@Composable
fun BottomBar(
    currentViewModel: BaseViewModel<*, *>,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        BottomAppBar(
            actions = {
                IconButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = {
                        if (currentViewModel.appScreen != AppScreen.Settings) {
                            navHostController.navigate(route = AppScreen.Settings.name)
                        }
                    },
                ) {
                    val settingsIcon =
                        if (currentViewModel.appScreen == AppScreen.Settings) {
                            Icons.Filled.Settings
                        } else {
                            Icons.Outlined.Settings
                        }
                    Icon(
                        modifier = Modifier.size(IconSize.Large.size),
                        imageVector = settingsIcon,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                /**
                 IconButton(
                 onClick = { /*TODO*/ },
                 ) {
                 val infoIcon =
                 if (currentViewModel.appScreen == AppScreen.Info) {
                 Icons.Filled.Info
                 } else {
                 Icons.Outlined.Info
                 }
                 Icon(
                 modifier = Modifier.size(IconSize.Large.size),
                 imageVector = infoIcon,
                 contentDescription = "Info",
                 tint = MaterialTheme.colorScheme.primary,
                 )
                 }
                 */
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = currentViewModel.floatingActionButtonAction,
                ) {
                    currentViewModel.floatingActionButton()
                }
            },
        )
    }
}
