package com.app.restaurantlogger

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.restaurantlogger.mvi.BaseViewModel
import com.app.restaurantlogger.screens.home.mvi.HomeViewModel
import com.app.restaurantlogger.screens.home.ui.homeScreen
import com.app.restaurantlogger.screens.log.mvi.LogViewModel
import com.app.restaurantlogger.screens.log.ui.logScreen
import com.app.restaurantlogger.screens.settings.mvi.SettingsViewModel
import com.app.restaurantlogger.screens.settings.ui.settingsScreen
import com.app.restaurantlogger.ui.BottomBar
import com.app.restaurantlogger.ui.TopBar
import com.app.restaurantlogger.ui.theme.RestaurantLoggerTheme
import com.app.restaurantlogger.ui.topBarHeight
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val logViewModel: LogViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val preferences =
                LocalContext.current.getSharedPreferences(
                    PREFERENCES_FILE_NAME,
                    Context.MODE_PRIVATE,
                )

            val initialTheme =
                preferences.getBoolean(
                    SettingNames.IsDarkTheme.name,
                    isSystemInDarkTheme(),
                )

            var isDarkTheme by remember {
                mutableStateOf(initialTheme)
            }

            RestaurantLoggerTheme(
                darkTheme = isDarkTheme,
            ) {
                RestaurantLoggerApp(
                    viewModels =
                        listOf(
                            homeViewModel,
                            logViewModel,
                            settingsViewModel,
                        ),
                    onThemeChange = { newState -> isDarkTheme = newState },
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RestaurantLoggerApp(
    viewModels: List<BaseViewModel<*, *>>,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen =
        AppScreen.valueOf(
            backStackEntry?.destination?.route?.substringBefore("/") ?: InitialAppScreen.name,
        )

    val currentViewModel = viewModels.getViewModel(currentScreen)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(currentViewModel = currentViewModel, navHostController = navController)
        },
        bottomBar = {
            BottomBar(
                currentViewModel = currentViewModel,
                navHostController = navController,
            )
        },
    ) {
        AnimatedNavHost(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = topBarHeight),
            navController = navController,
            startDestination = AppScreen.Home.name,
        ) {
            homeScreen(
                homeViewModel = viewModels.getViewModel(AppScreen.Home) as HomeViewModel,
                navController = navController,
            )
            logScreen(
                logViewModel = viewModels.getViewModel(AppScreen.Log) as LogViewModel,
            )
            settingsScreen(
                settingsViewModel = viewModels.getViewModel(AppScreen.Settings) as SettingsViewModel,
                onThemeChange = onThemeChange,
            )
        }
    }
}

fun List<BaseViewModel<*, *>>.getViewModel(appScreen: AppScreen) = this.first { it.appScreen == appScreen }

enum class AppScreen {
    Home,
    Log,
    Settings,
    Info,
}

val InitialAppScreen = AppScreen.Home
