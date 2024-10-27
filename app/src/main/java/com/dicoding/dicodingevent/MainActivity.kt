package com.dicoding.dicodingevent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.app.AppCompatDelegate
//import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
//import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
//import com.dicoding.dicodingevent.data.ThemePreferences
//import com.dicoding.dicodingevent.data.dataStore
import com.dicoding.dicodingevent.databinding.ActivityMainBinding
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private lateinit var themePreferences: ThemePreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize theme preferences
//        themePreferences = ThemePreferences.getInstance(dataStore)
//
//        // Apply theme before inflating layout
//        lifecycleScope.launch {
//            val isDarkModeActive = themePreferences.getThemeSetting().first()
//            updateTheme(isDarkModeActive)
//        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_active, R.id.navigation_past, R.id.navigation_settings
//            )
//        )
        // setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}