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
import javax.inject.Named

const val MAIN_VM = "mainactivityVm"
const val MAIN_FRAGMENT_VM = "mainfragmentvm"
@Module
object MainActivityModule {

    @JvmStatic
    @Provides
    fun getAstronomyPicDao(db: AstronomyPicDatabase): AstronomyPicDao = db.astronomyPicDao()

    @JvmStatic
    @Provides
    fun provideApodApi(retrofit: Retrofit): ApodApi = retrofit.create(ApodApi::class.java)

    @JvmStatic
    @Provides
    @Named(MAIN_VM)
    fun provideMainVm(mainActivity: MainActivity, factory: AppodViewModelFactory): MainVm =
        ViewModelProviders.of(mainActivity, factory).get(MainVm::class.java)

    @JvmStatic
    @Provides
    @Named(MAIN_FRAGMENT_VM)
    fun provideFragmentVm(mainActivity: MainActivity): MainVm =
        ViewModelProviders.of(mainActivity).get(MainVm::class.java)
}