package com.aniketkadam.appod.mainscreen

import android.os.Bundle
import com.aniketkadam.appod.R
import com.aniketkadam.appod.mainscreen.vm.MainVm
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mainVm: MainVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
