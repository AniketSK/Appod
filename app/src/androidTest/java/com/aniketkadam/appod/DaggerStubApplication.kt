package com.aniketkadam.appod

import com.aniketkadam.appod.di.DaggerAppComponentTest
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber

class DaggerStubApplication : DaggerApplication() {

    private val applicationInjector by lazy {
        DaggerAppComponentTest.factory().create(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        Timber.plant(Timber.DebugTree())
    }


}