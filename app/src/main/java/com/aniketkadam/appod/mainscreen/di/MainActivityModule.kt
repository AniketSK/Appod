package com.aniketkadam.appod.mainscreen.di

import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.database.AstronomyPicDao
import com.aniketkadam.appod.data.database.AstronomyPicDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainActivityModule {
    @Provides
    fun getAstronomyPicDao(db: AstronomyPicDatabase): AstronomyPicDao = db.astronomyPicDao()

    @Provides
    fun provideApodApi(retrofit: Retrofit): ApodApi = retrofit.create(ApodApi::class.java)
}