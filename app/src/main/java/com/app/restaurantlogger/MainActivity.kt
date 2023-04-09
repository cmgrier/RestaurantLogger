package com.app.restaurantlogger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.restaurantlogger.home.mvi.HomeScreen
import com.app.restaurantlogger.home.mvi.HomeViewModel
import com.app.restaurantlogger.log.mvi.LogScreen
import com.app.restaurantlogger.log.mvi.LogViewModel
import com.app.restaurantlogger.ui.enterZoomLeft
import com.app.restaurantlogger.ui.enterZoomRight
import com.app.restaurantlogger.ui.exitZoomLeft
import com.app.restaurantlogger.ui.exitZoomRight
import com.app.restaurantlogger.ui.theme.RestaurantLoggerTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantLoggerTheme {
                RestaurantLoggerApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RestaurantLoggerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Home.title
    )

    // ViewModels
    val homeViewModel = HomeViewModel()
    val logViewModel = LogViewModel()

    Scaffold (
        modifier = modifier.fillMaxSize()
    ){
        AnimatedNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = AppScreen.Home.title,
        ) {
            composable(
                route = AppScreen.Home.title,
                enterTransition = {
                    enterZoomLeft()
                },
                exitTransition = {
                    exitZoomLeft()
                },
                popEnterTransition = {
                    enterZoomRight()
                }
            ) {
                HomeScreen(viewModel = homeViewModel, navHostController = navController)
            }
            composable(
                route = AppScreen.Log.title,
                enterTransition = {
                    enterZoomLeft()
                },
                exitTransition = {
                    exitZoomLeft()
                },
                popExitTransition = {
                    exitZoomRight()
                }
            ) {
                LogScreen(viewModel = logViewModel, navHostController = navController)
            }
        }
    }

}

enum class AppScreen(val title: String) {
    Home("Home"),
    Log("Log")
}