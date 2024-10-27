package com.dicoding.dicodingevent.data

import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getActiveEvents(limit: Int = 20): List<ListEventsItem>
    suspend fun getPastEvents(limit: Int = 20): List<ListEventsItem>

    // Metode untuk manajemen favorit
    fun getAllFavorites(): Flow<List<FavoriteEvent>>
    suspend fun insertFavorite(event: FavoriteEvent)
    suspend fun deleteFavorite(event: FavoriteEvent)

    // mETODE MANAJEMEN notifiacion
    suspend fun getNearestActiveEvent(): ListEventsItem?
}