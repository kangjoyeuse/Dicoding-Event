package com.dicoding.dicodingevent.api

import com.dicoding.dicodingevent.data.EventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
//    Contoh api endpoint untuk mendapatkan event
//    @GET("events")
//    suspend fun getEvents(@Query("active") active: Int): EventResponse


//    @GET("events")
//    suspend fun searchEvents(@Query("active") active: Int, @Query("q") query: String): EventResponse

//    Note: Gunakan getEvents untuk mendapatkan event atau dipisah satu persatu

    @GET("events")
    suspend fun getActiveEvents(@Query("active") active: Int = 1, @Query("limit") limit: Int = 20): EventResponse

    // Testing only
    @GET("events")
    suspend fun getPastEvents(@Query("active") active: Int = 0, @Query("limit") limit: Int = 20): EventResponse
    @GET("events")

    suspend fun getNotificationEvents(@Query("active") active: Int = -1, @Query("limit") limit: Int = 1): EventResponse
}