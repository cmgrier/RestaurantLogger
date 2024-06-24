package com.app.restaurantlogger.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.restaurantlogger.mvi.BaseViewModel
import com.app.restaurantlogger.ui.theme.LocalEdgePadding
import com.app.restaurantlogger.util.thenIf
import kotlinx.coroutines.delay

val topBarHeight = 60.dp

@Composable
fun TopBar(
    currentViewModel: BaseViewModel<*, *>,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column {
        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .height(topBarHeight),
            verticalArrangement = Arrangement.Bottom,
        ) {
            val fadeDuration = 200
            var headerVisible by remember { mutableStateOf(true) }
            var topBarViewModel by remember { mutableStateOf(currentViewModel) }
            LaunchedEffect(key1 = currentViewModel) {
                headerVisible = false
                delay(fadeDuration.toLong())
                topBarViewModel = currentViewModel
                headerVisible = true
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = headerVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = fadeDuration)),
                exit = fadeOut(animationSpec = tween(durationMillis = fadeDuration)),
            ) {
                topBarViewModel.topBarContent(navHostController, Modifier)
            }
        }
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Composable
fun TopBarHeader(
    header: String,
    modifier: Modifier = Modifier,
    hasBackButton: Boolean = false,
    navHostController: NavHostController? = null,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (hasBackButton && navHostController != null) {
            BackButton(navHostController = navHostController)
        }
        val edgePadding = LocalEdgePadding.current
        Text(
            modifier =
                modifier.weight(1f).thenIf(!hasBackButton) {
                    this.padding(horizontal = edgePadding)
                },
            text = header,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}
