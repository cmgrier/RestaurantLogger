package com.app.restaurantlogger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import com.app.restaurantlogger.home.mvi.HomeScreen
import com.app.restaurantlogger.home.mvi.HomeViewModel
import com.app.restaurantlogger.ui.theme.RestaurantLoggerTheme
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantLoggerTheme {
                // A surface container using the 'background' color from the theme
                Scaffold {
                    HomeScreen(viewModel = HomeViewModel())
                }
            }
        }
    }
}