package com.aniketkadam.appod.di

import androidx.lifecycle.ViewModelProviders
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.database.AstronomyPicDao
import com.aniketkadam.appod.data.database.AstronomyPicDatabase
import com.aniketkadam.appod.mainscreen.MainActivity
import com.aniketkadam.appod.mainscreen.di.MAIN_FRAGMENT_VM
import com.aniketkadam.appod.mainscreen.di.MAIN_VM
import com.aniketkadam.appod.mainscreen.vm.AppodViewModelFactory
import com.aniketkadam.appod.mainscreen.vm.MainVm
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class MainActivityModuleForTest {
    @Provides
    fun getAstronomyPicDao(db: AstronomyPicDatabase): AstronomyPicDao = db.astronomyPicDao()

    @Provides
    fun provideApodApi(retrofit: Retrofit): ApodApi = retrofit.create(ApodApi::class.java)


    @Provides
    @Named(MAIN_VM)
    fun provideMainVm(mainActivity: MainActivity, factory: AppodViewModelFactory): MainVm =
        ViewModelProviders.of(mainActivity, factory).get(MainVm::class.java)

    @Provides
    @Named(MAIN_FRAGMENT_VM)
    fun provideFragmentVm(mainActivity: MainActivity): MainVm =
        ViewModelProviders.of(mainActivity).get(MainVm::class.java)

}
