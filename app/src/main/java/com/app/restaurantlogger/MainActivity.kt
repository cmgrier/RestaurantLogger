package com.app.restaurantlogger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.restaurantlogger.database.AppDatabase
import com.app.restaurantlogger.database.ReviewDao
import com.app.restaurantlogger.home.mvi.HomeScreen
import com.app.restaurantlogger.home.mvi.HomeViewModel
import com.app.restaurantlogger.home.mvi.homeScreen
import com.app.restaurantlogger.log.mvi.LogScreen
import com.app.restaurantlogger.log.mvi.LogViewModel
import com.app.restaurantlogger.log.mvi.logScreen
import com.app.restaurantlogger.mvi.BaseIntent
import com.app.restaurantlogger.mvi.BaseState
import com.app.restaurantlogger.mvi.BaseViewModel
import com.app.restaurantlogger.ui.enterZoomLeft
import com.app.restaurantlogger.ui.enterZoomRight
import com.app.restaurantlogger.ui.exitZoomLeft
import com.app.restaurantlogger.ui.exitZoomRight
import com.app.restaurantlogger.ui.theme.RestaurantLoggerTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val logViewModel: LogViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantLoggerTheme {
                RestaurantLoggerApp(
                    viewModels = listOf(
                        homeViewModel,
                        logViewModel,
                    )
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RestaurantLoggerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    viewModels: List<BaseViewModel<*,*>>,
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route?.substringBefore("/") ?: AppScreen.Home.name
    )

    val currentViewModel = viewModels.getViewModel(currentScreen)

    Scaffold (
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = currentViewModel.floatingActionButtonAction,
            ) {
                currentViewModel.floatingActionButton()
            }
        },
        topBar = currentViewModel.topBar,
    ){
        AnimatedNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = AppScreen.Home.name,
        ) {
            homeScreen(
                homeViewModel = viewModels.getViewModel(AppScreen.Home) as HomeViewModel,
                navController = navController,
            )
            logScreen(
                logViewModel = viewModels.getViewModel(AppScreen.Log) as LogViewModel,
                navController = navController,
            )
        }
    }

}

fun List<BaseViewModel<*,*>>.getViewModel(appScreen: AppScreen) = this.first { it.appScreen == appScreen }

enum class AppScreen {
    Home,
    Log,
}