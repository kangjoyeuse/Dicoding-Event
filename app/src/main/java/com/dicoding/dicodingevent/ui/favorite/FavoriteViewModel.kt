package com.dicoding.dicodingevent.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.data.FavoriteEvent
import com.dicoding.dicodingevent.data.Repository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    private val _favorites = MutableLiveData<List<FavoriteEvent>>()
    val favorites: LiveData<List<FavoriteEvent>> = _favorites

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllFavorites().collect { favoriteList ->
                _favorites.value = favoriteList
                _isLoading.value = false
            }
        }
    }

    fun removeFavorite(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            repository.deleteFavorite(favoriteEvent)
        }
    }
}