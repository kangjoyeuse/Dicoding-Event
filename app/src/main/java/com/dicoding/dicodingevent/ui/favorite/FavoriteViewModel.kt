package com.dicoding.dicodingevent.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.data.AppDatabase
import com.dicoding.dicodingevent.data.FavoriteEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val database: AppDatabase) : ViewModel() {
    private val favoriteDao = database.favoriteEventDao()

    // Get all favorites as LiveData
    val allFavorites = favoriteDao.getAllFavorites().asLiveData()

    // Add a new favorite event
    fun addFavorite(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            favoriteDao.insertFavorite(favoriteEvent)
        }
    }

    // Delete a favorite event
    fun deleteFavorite(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            favoriteDao.deleteFavorite(favoriteEvent)
        }
    }

    // Delete a favorite event by ID
    fun deleteFavoriteById(eventId: String) {
        viewModelScope.launch {
            favoriteDao.deleteFavoriteById(eventId)
        }
    }

    // Check if an event is favorited
    fun isEventFavorited(eventId: String): Flow<Boolean> {
        return favoriteDao.isEventFavorited(eventId)
    }

    // Get a favorite event by ID
    suspend fun getFavoriteById(eventId: String): FavoriteEvent? {
        return favoriteDao.getFavoriteById(eventId)
    }
}

class FavoriteViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}