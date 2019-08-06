package com.aniketkadam.appod.di

import com.aniketkadam.appod.mainscreen.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModuleForTest {

    @ContributesAndroidInjector(modules = [MainActivityModuleForTest::class, MainActivityFragmentBuilder::class])
    abstract fun bindMainActivity(): MainActivity
}