package com.aniketkadam.appod

import com.aniketkadam.appod.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class AppodApplication : DaggerApplication() {

    private val appInjector by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appInjector
}