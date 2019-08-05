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
        val result = req.getDatesForNumItemsStartingToday(10, LocalDate.parse("2019-01-15"))
        assertThat(result, equalTo(ApodRequestDates("2019-01-06", "2019-01-15")))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `creating an apod request date with an end date earlier than the start date is prevented`() {
        ApodRequestDates("2019-01-15", "2019-01-06")
    }
}