package com.aniketkadam.appod.mainscreen.data

import androidx.test.core.app.ApplicationProvider
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.data.database.AstronomyPicDao
import net.danlew.android.joda.JodaTimeAndroid
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ApodNetworkBoundaryCallbackTestInstrumented {

    @Mock
    lateinit var api: ApodApi
    @Mock
    lateinit var dao: AstronomyPicDao

    lateinit var bc: ApodNetworkBoundaryCallback

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
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
        bc.onItemAtEndLoaded(AstronomyPic("", "", "", "", "2019-08-05"))
        verify(api, times(1)).getApodList(ApodRequestDates("2019-06-15", "2019-08-04"))
    }
}