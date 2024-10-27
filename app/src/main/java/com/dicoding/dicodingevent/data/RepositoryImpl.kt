package com.dicoding.dicodingevent.data

import com.dicoding.dicodingevent.api.ApiService
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val apiService: ApiService,
    private val favoriteEventDao: FavoriteEventDao
) : Repository {

    override suspend fun getActiveEvents(limit: Int): List<ListEventsItem> {
        return apiService.getActiveEvents(limit = limit).listEvents
    }

    override suspend fun getPastEvents(limit: Int): List<ListEventsItem> {
        return apiService.getPastEvents(limit = limit).listEvents
    }

    override fun getAllFavorites(): Flow<List<FavoriteEvent>> {
        return favoriteEventDao.getAllFavorites()
    }

    override suspend fun insertFavorite(event: FavoriteEvent) {
        favoriteEventDao.insertFavorite(event)
    }

    override suspend fun deleteFavorite(event: FavoriteEvent) {
        favoriteEventDao.deleteFavorite(event)
    }

    override suspend fun getNearestActiveEvent(): ListEventsItem? {
        return apiService.getNotificationEvents(limit = 1).listEvents.firstOrNull()
    }

//    suspend fun getFavoriteById(eventId: String): FavoriteEvent? {
//        return favoriteEventDao.getFavoriteById(eventId)
//    }
//
//    suspend fun deleteFavoriteById(eventId: String) {
//        favoriteEventDao.deleteFavoriteById(eventId)
//    }
//
//    fun isEventFavorited(eventId: String): Flow<Boolean> {
//        return favoriteEventDao.isEventFavorited(eventId)
//    }
}