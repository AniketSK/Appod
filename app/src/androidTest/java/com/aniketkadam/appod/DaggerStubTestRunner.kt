package com.aniketkadam.appod

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class DaggerStubTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, DaggerStubApplication::class.java.name, context)
    }
}