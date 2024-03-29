package com.aniketkadam.appod.di

import android.app.Application
import com.aniketkadam.appod.DaggerStubApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        DatabaseModuleForTest::class,
        ActivityBuilderModuleForTest::class,
        AppModule::class]
)
interface AppComponentTest : AndroidInjector<DaggerStubApplication> {


    @Component.Factory
    interface Factory {

        fun create(@BindsInstance app: Application): AppComponentTest

    }
}