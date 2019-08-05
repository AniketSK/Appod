package com.aniketkadam.appod.data.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.data.getValueForLiveData
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class AstronomyPicDatabaseTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AstronomyPicDatabase
    private lateinit var dao: AstronomyPicDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AstronomyPicDatabase::class.java).allowMainThreadQueries().build()
        dao = db.astronomyPicDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = db.close()

    @Test
    fun `inserting and retrieving data works`() {

        val sampleData = listOf(
            AstronomyPic(
                "title", "google.com", "google.com/hd",
                "explain",
                "2009-12-02"
            )
        )

        dao.insert(sampleData)
        assertThat(sampleData.get(0), equalTo<AstronomyPic>(getValueForLiveData(dao.getAstronomyPics()).get(0)))
    }
}