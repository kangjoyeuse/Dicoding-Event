package com.dicoding.dicodingevent.di

import android.content.Context
import com.dicoding.dicodingevent.api.ApiConfig
import com.dicoding.dicodingevent.data.AppDatabase
import com.dicoding.dicodingevent.data.Repository
import com.dicoding.dicodingevent.data.RepositoryImpl

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = AppDatabase.getDatabase(context)
        val apiService = ApiConfig.apiService
        return RepositoryImpl(apiService, database.favoriteEventDao())
    }
}