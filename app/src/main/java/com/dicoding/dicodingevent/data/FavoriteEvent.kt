package com.dicoding.dicodingevent.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
data class FavoriteEvent(
    @PrimaryKey val id: Int,
    val name: String,
    val ownerName: String,
    val beginTime: String,
    val endTime: String,
    val quota: Int,
    val registrants: Int,
    val description: String,
    val imageLogo: String,
    val link: String
)
