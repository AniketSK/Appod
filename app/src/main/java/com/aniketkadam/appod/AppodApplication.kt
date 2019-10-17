package com.aniketkadam.appod

import com.aniketkadam.appod.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import net.danlew.android.joda.JodaTimeAndroid


class AppodApplication : DaggerApplication() {

    private val appInjector by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appInjector
}