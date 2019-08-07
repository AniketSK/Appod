package com.aniketkadam.appod.di

import com.aniketkadam.appod.mainscreen.apoddetail.DetailFragment
import com.aniketkadam.appod.mainscreen.apodlist.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentBuilder {
    @ContributesAndroidInjector
    abstract fun bindListFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun bindDetailFragment(): DetailFragment
}