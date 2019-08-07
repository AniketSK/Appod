package com.aniketkadam.appod.di

import androidx.lifecycle.ViewModelProviders
import androidx.test.platform.app.InstrumentationRegistry
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.data.database.AstronomyPicDao
import com.aniketkadam.appod.data.database.AstronomyPicDatabase
import com.aniketkadam.appod.mainscreen.MainActivity
import com.aniketkadam.appod.mainscreen.data.ApodRequestDates
import com.aniketkadam.appod.mainscreen.di.MAIN_FRAGMENT_VM
import com.aniketkadam.appod.mainscreen.di.MAIN_VM
import com.aniketkadam.appod.mainscreen.vm.AppodViewModelFactory
import com.aniketkadam.appod.mainscreen.vm.MainVm
import com.aniketkadam.appod.readAssetsFile
import dagger.Module
import dagger.Provides
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.reactivex.Single
import org.joda.time.LocalDate
import javax.inject.Named

@Module
class MainActivityModuleForTest {
    @Provides
    fun getAstronomyPicDao(db: AstronomyPicDatabase): AstronomyPicDao = db.astronomyPicDao()

    @Provides
    fun provideApodApi(): ApodApi {
        val api = mockk<ApodApi> { ApodApi::class.java }
        val slot = slot<ApodRequestDates>()

        val fullApodList: List<AstronomyPic> =
            InstrumentationRegistry.getInstrumentation().context.readAssetsFile("sampledata/apod.json")

        every { api.getApodList(capture(slot)) } answers {
            val a: ApodRequestDates = slot.captured
            val lowerLimit = LocalDate.parse(a.get("start_date"))
            val upperLimit = LocalDate.parse(a.get("end_date"))
            val result = fullApodList.filter {
                val current = LocalDate.parse(it.date)
                current.isBefore(upperLimit) && current.isAfter(lowerLimit)
            }
            Single.just(result)

        }
        return api
    }

    @Provides
    @Named(MAIN_VM)
    fun provideMainVm(mainActivity: MainActivity, factory: AppodViewModelFactory): MainVm =
        ViewModelProviders.of(mainActivity, factory).get(MainVm::class.java)

    @Provides
    @Named(MAIN_FRAGMENT_VM)
    fun provideFragmentVm(mainActivity: MainActivity): MainVm =
        ViewModelProviders.of(mainActivity).get(MainVm::class.java)

}
