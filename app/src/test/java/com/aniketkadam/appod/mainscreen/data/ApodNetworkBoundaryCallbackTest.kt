package com.aniketkadam.appod.mainscreen.data

import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.data.database.AstronomyPicDao
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as When

class ApodNetworkBoundaryCallbackTest {

    @Mock
    lateinit var api: ApodApi
    @Mock
    lateinit var dao: AstronomyPicDao
    @Mock
    lateinit var requestDates: ApodRequestDates
    lateinit var b: ApodNetworkBoundaryCallback
    private val pic = AstronomyPic("", "", "", "", "")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        When(api.getApodList(requestDates)).thenReturn(Single.just(listOf(pic)))
    }

    @Test
    fun `when the zero items loaded call is made, the items are not actually loaded more than once`() {
        b = ApodNetworkBoundaryCallback(api, dao, requestDates)
        b.onZeroItemsLoaded()
        b.onZeroItemsLoaded()
        verify(api, times(1)).getApodList(requestDates)
        verify(dao, times(1)).insert(listOf(pic))
    }

    @Test
    fun `when an item at the end is requeted to load, a network call is made and data is stored`() {
        b = ApodNetworkBoundaryCallback(api, dao, requestDates)
        b.onItemAtEndLoaded(pic)
        verify(api, times(1)).getApodList(requestDates)
        verify(dao, times(1)).insert(listOf(pic))
    }
}
