package com.aniketkadam.appod.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AstronomyPic(
    val title: String,
    val url: String,
    val hdurl: String,
    val explanation: String,
    @PrimaryKey val date: String
)