package com.aniketkadam.appod.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class AstronomyPic(
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("hdurl") val hdurl: String?,
    @field:SerializedName("explanation") val explanation: String,
    @field:SerializedName("date") @PrimaryKey val date: String,
    val bookmark: Boolean? = null // TODO this null won't actually set it to null when it comes from json
)