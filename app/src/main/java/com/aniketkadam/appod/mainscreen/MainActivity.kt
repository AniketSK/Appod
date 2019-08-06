package com.aniketkadam.appod.mainscreen

import android.os.Bundle
import com.aniketkadam.appod.R
import com.aniketkadam.appod.mainscreen.di.MAIN_VM
import com.aniketkadam.appod.mainscreen.vm.MainVm
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import javax.inject.Named

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    @field:Named(MAIN_VM)
    lateinit var mainVm: MainVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
