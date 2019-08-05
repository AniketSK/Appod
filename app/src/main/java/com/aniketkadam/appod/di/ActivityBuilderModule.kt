package com.aniketkadam.appod.di

import com.aniketkadam.appod.mainscreen.MainActivity
import com.aniketkadam.appod.mainscreen.di.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun getMainActivity(): MainActivity
}