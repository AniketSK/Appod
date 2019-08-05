package com.aniketkadam.appod.mainscreen.data

import androidx.test.core.app.ApplicationProvider
import net.danlew.android.joda.JodaTimeAndroid
import org.hamcrest.Matchers.equalTo
import org.joda.time.LocalDate
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class) // Dan lew's Jodatime is very optimized for android, we could swap it out
//  to use with the regular jodatime in tests but let's not right now.
class ApodRequestConstructorTest {
    lateinit var req: ApodRequestConstructor

    @Before
    fun setUp() {
        JodaTimeAndroid.init(ApplicationProvider.getApplicationContext())
        req = ApodRequestConstructor()
    }

    @Test
    fun `calling get num items from today returns appropriate answers`() {
        val result = req.getDatesForNumItemsStartingToday(10, LocalDate.parse("2019-01-10"))
        assertThat(result, equalTo(ApodRequestDates("2019-01-01", "2019-01-10")))

    }
}