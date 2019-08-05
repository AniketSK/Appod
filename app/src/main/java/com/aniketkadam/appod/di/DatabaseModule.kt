package com.aniketkadam.appod.di

import android.content.Context
import androidx.room.Room
import com.aniketkadam.appod.data.database.AstronomyPicDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun getDatabase(context: Context): AstronomyPicDatabase =
        Room.databaseBuilder(context, AstronomyPicDatabase::class.java, "astronomy_pic_database")
            .build()

}