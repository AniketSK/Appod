package com.aniketkadam.appod.mainscreen.di

import androidx.lifecycle.ViewModelProviders
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.database.AstronomyPicDao
import com.aniketkadam.appod.data.database.AstronomyPicDatabase
import com.aniketkadam.appod.mainscreen.MainActivity
import com.aniketkadam.appod.mainscreen.vm.AppodViewModelFactory
import com.aniketkadam.appod.mainscreen.vm.MainVm
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainActivityModule {
    @Provides
    fun getAstronomyPicDao(db: AstronomyPicDatabase): AstronomyPicDao = db.astronomyPicDao()

    @Provides
    fun provideApodApi(retrofit: Retrofit): ApodApi = retrofit.create(ApodApi::class.java)

    @Provides
    fun provideMainVm(mainActivity: MainActivity, factory: AppodViewModelFactory): MainVm =
        ViewModelProviders.of(mainActivity, factory).get(MainVm::class.java)
}