package com.app.restaurantlogger

import android.app.Application
import com.app.restaurantlogger.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject lateinit var AppDatabase: AppDatabase
}