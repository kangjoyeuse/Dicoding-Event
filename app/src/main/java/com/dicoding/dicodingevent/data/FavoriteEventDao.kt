package com.dicoding.dicodingevent.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEvent: FavoriteEvent)

    @Query("SELECT * FROM favorite_events")
    fun getAllFavorites(): Flow<List<FavoriteEvent>>

    @Query("SELECT * FROM favorite_events WHERE id = :eventId")
    suspend fun getFavoriteById(eventId: String): FavoriteEvent?

    @Delete
    suspend fun deleteFavorite(favoriteEvent: FavoriteEvent)

    @Query("DELETE FROM favorite_events WHERE id = :eventId")
    suspend fun deleteFavoriteById(eventId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_events WHERE id = :eventId)")
    fun isEventFavorited(eventId: String): Flow<Boolean>
}