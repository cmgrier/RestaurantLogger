package com.app.restaurantlogger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.restaurantlogger.home.mvi.HomeScreen
import com.app.restaurantlogger.home.mvi.HomeViewModel
import com.app.restaurantlogger.ui.theme.RestaurantLoggerTheme

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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantLoggerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Home.title
    )

    val homeViewModel = HomeViewModel()

    Scaffold (
        modifier = modifier
    ){
        NavHost(
            navController = navController,
            startDestination = AppScreen.Home.title
        ) {
            composable(route = AppScreen.Home.title) {
                HomeScreen(viewModel = homeViewModel, navHostController = navController)
            }
            composable(route = AppScreen.Log.title) {
                TextButton(
                    onClick = { navController.navigate(AppScreen.Home.title) }
                ) {
                    Text(text = "Go Back")
                }
            }
        }
    }

}

enum class AppScreen(val title: String) {
    Home("Home"),
    Log("Log")
}