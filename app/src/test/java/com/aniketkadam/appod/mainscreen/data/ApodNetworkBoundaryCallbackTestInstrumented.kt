package com.aniketkadam.appod.mainscreen.data

import androidx.test.core.app.ApplicationProvider
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.database.AstronomyPicDao
import com.aniketkadam.appod.data.emptyAstronomyPic
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import net.danlew.android.joda.JodaTimeAndroid
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
class ApodNetworkBoundaryCallbackTestInstrumented {

    @MockK(relaxed = true)
    lateinit var api: ApodApi
    @MockK(relaxed = true)
    lateinit var dao: AstronomyPicDao

    lateinit var bc: ApodNetworkBoundaryCallback

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        JodaTimeAndroid.init(ApplicationProvider.getApplicationContext())
        bc = ApodNetworkBoundaryCallback(api, dao)
    }

    @Test
    fun `when items at end is called, it calcualtes the next sequence of dates correctly`() {
        assertThat(
            "The dates below rely on the prefetch distance being 50. Change the dates if that distance changes. I suggest googling 'x days from yesterday' and using now + that date for it",
            PREFETCH_DISTANCE,
            equalTo(50)
        )
        bc.onItemAtEndLoaded(emptyAstronomyPic().copy(date = "2019-08-05"))
        verify(exactly = 1) { api.getApodList(ApodRequestDates("2019-06-15", "2019-08-04")) }
    }

    @Test
    fun `the database calls are made once onItematEnd are loaded from the net`() {
        every { api.getApodList(any()) } returns Single.just(listOf(emptyAstronomyPic()))
        bc.onItemAtEndLoaded(emptyAstronomyPic().copy(date = "2019-08-05"))
        verify(exactly = 1) { dao.insert(allAny()) }
    }

    @Test
    fun `onItemAtEndLoaded should not fire twice for calls while one is in progress`() {
        every { api.getApodList(any()) } returns Single.just(listOf(emptyAstronomyPic()))

        val ts = TestScheduler()
        RxJavaPlugins.setIoSchedulerHandler { ts }
        bc.onItemAtEndLoaded(emptyAstronomyPic().copy(date = "2019-08-05"))
        bc.onItemAtEndLoaded(emptyAstronomyPic().copy(date = "2019-08-05"))
        ts.advanceTimeBy(5, TimeUnit.SECONDS)
        verify(exactly = 1) { api.getApodList(any()) }

        RxJavaPlugins.reset()
    }
}