package com.aniketkadam.appod.di

import com.aniketkadam.appod.data.ApodApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApodApiModule {

    @Provides
    fun provideApodApi(retrofit: Retrofit) = retrofit.create(ApodApi::class.java)

}