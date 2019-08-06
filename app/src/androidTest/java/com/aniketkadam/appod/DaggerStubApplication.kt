package com.aniketkadam.appod

import com.aniketkadam.appod.di.DaggerAppComponentTest
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import net.danlew.android.joda.JodaTimeAndroid

class DaggerStubApplication : DaggerApplication() {

    private val applicationInjector by lazy {
        DaggerAppComponentTest.builder().bindApplication(this).build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }


}