package com.aniketkadam.appod.di

import android.app.Application
import com.aniketkadam.appod.DaggerStubApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        DatabaseModuleForTest::class,
        NetworkingModule::class,
        ActivityBuilderModuleForTest::class,
        AppModule::class]
)
interface AppComponentTest : AndroidInjector<DaggerStubApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplication(app: Application): Builder

        fun build(): AppComponentTest
    }
}