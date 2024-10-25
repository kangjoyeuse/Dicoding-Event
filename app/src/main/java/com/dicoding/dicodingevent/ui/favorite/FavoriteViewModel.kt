package com.dicoding.dicodingevent.ui.favorite

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.data.AppDatabase


class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val favoriteDao = database.favoriteEventDao()

    fun getAllFavorites() = favoriteDao.getAllFavorites()
}