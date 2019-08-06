package com.aniketkadam.appod.di

import com.aniketkadam.appod.mainscreen.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentBuilder {
    @ContributesAndroidInjector
    abstract fun bindListFragment(): ListFragment
}