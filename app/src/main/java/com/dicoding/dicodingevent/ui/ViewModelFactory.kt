package com.dicoding.dicodingevent.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.data.Repository
import com.dicoding.dicodingevent.di.Injection
import com.dicoding.dicodingevent.ui.active.ActiveViewModel
import com.dicoding.dicodingevent.ui.past.PastViewModel
import com.dicoding.dicodingevent.ui.favorite.FavoriteViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ActiveViewModel::class.java) -> {
                ActiveViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PastViewModel::class.java) -> {
                PastViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}