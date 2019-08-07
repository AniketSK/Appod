package com.aniketkadam.appod.di

import androidx.lifecycle.ViewModelProviders
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
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import javax.inject.Named

@Module
class MainActivityModuleForTest {
    @Provides
    fun getAstronomyPicDao(db: AstronomyPicDatabase): AstronomyPicDao = db.astronomyPicDao()

    @Provides
    fun provideApodApi(): ApodApi =
        object : ApodApi {
            override fun getApodList(startEnd: ApodRequestDates, apiKey: String): Single<List<AstronomyPic>> {
                return Single.just(
                    listOf(
                        AstronomyPic(
                            "A Total Solar Eclipse Reflected",
                            "https://apod.nasa.gov/apod/image/1908/DoubleEclipse_Legault_1080.jpg",
                            "https://apod.nasa.gov/apod/image/1908/DoubleEclipse_Legault_1920.jpg",
                            "If you saw a total solar eclipse, would you do a double-take? One astrophotographer did just that -- but it took a lake and a bit of planning. Realizing that the eclipse would be low on the horizon, he looked for a suitable place along the thin swath of South America that would see, for a few minutes, the Moon completely block the Sun, both directly and in reflection.  The day before totality, he visited a lake called La Cuesta Del Viento (The Slope of the Wind) and, despite its name, found so little wind that the lake looked like a mirror.  Perfect.  Returning the day of the eclipse, though, there was a strong breeze  churning up the water -- enough to ruin the eclipse reflection shot.  Despair. But wait!  Strangely, about an hour before totality, the wind died down.  This calmness may have been related to the eclipse itself, because eclipsed ground heats the air less and reduces the amount rising warm air -- which can dampen and even change the wind direction.  The eclipse came, his tripod and camera were ready, and so was the lake. The featured image of this double-eclipse came from a single exposure lasting just one fifteenth of a second. Soon after totality, the winds returned and the water again became choppy. No matter -- this double-image of the 2019 July total solar eclipse had been captured forever.",
                            "2019-08-05"
                        )
                    )
                )
            }

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
