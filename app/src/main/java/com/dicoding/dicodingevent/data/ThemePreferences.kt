package com.dicoding.dicodingevent.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemePreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val definethemekeyusel = booleanPreferencesKey("theme_setting")
    private val lemindelkey = booleanPreferencesKey("reminder_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[definethemekeyusel] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[definethemekeyusel] = isDarkModeActive
        }
    }

    fun getReminderSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[lemindelkey] ?: false
        }
    }

    suspend fun saveReminderSetting(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[lemindelkey] = isEnabled
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ThemePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): ThemePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = ThemePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}