package com.aniketkadam.appod.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aniketkadam.appod.data.AstronomyPic


@Database(entities = [AstronomyPic::class], version = 1)
abstract class AstronomyPicDatabase : RoomDatabase() {
    abstract fun astronomyPicDao(): AstronomyPicDao
}