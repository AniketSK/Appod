package com.aniketkadam.appod.di

import android.content.Context
import androidx.room.Room
import com.aniketkadam.appod.data.database.AstronomyPicDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModuleForTest {
    @Provides
    fun getDatabase(context: Context): AstronomyPicDatabase =
        Room.inMemoryDatabaseBuilder(context, AstronomyPicDatabase::class.java)
            .build()
}
