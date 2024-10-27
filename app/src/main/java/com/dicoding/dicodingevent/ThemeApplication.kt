package com.dicoding.dicodingevent

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.dicodingevent.data.ThemePreferences
import com.dicoding.dicodingevent.data.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ThemeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val themePreferences = ThemePreferences.getInstance(dataStore)

        runBlocking {
            val isDarkModeActive = themePreferences.getThemeSetting().first()
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}