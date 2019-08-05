package com.aniketkadam.appod

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class DaggerStubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }
}