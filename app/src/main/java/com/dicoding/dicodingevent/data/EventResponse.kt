package com.dicoding.dicodingevent.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class EventResponse(
	@field:SerializedName("listEvents")
	val listEvents: List<ListEventsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class ListEventsItem(
	@SerializedName("summary") val summary: String,
	@SerializedName("registrants") val registrants: Int,
	@SerializedName("imageLogo") val imageLogo: String,
	@SerializedName("ownerName") val ownerName: String,
	@SerializedName("quota") val quota: Int,
	@SerializedName("name") val name: String,
	@SerializedName("link") val link: String,
	@SerializedName("description") val description: String,
	@SerializedName("id") val id: Int,
	@SerializedName("beginTime") val beginTime: String,
	@SerializedName("endTime") val endTime: String
) : Parcelable