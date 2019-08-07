package com.aniketkadam.appod.mainscreen.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aniketkadam.appod.RxImmediateSchedulerRule
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.data.database.AstronomyPicDao
import com.aniketkadam.appod.data.getValueForLiveData
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ApodNetworkBoundaryCallbackTest {

    @MockK
    lateinit var api: ApodApi
    @MockK
    lateinit var dao: AstronomyPicDao
    @MockK
    lateinit var requestDates: ApodRequestDates
    lateinit var b: ApodNetworkBoundaryCallback
    private val pic = AstronomyPic("", "", "", "", "")

    @get:Rule
    val immediateSchedulerRule = RxImmediateSchedulerRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { api.getApodList(any()) } answers { Single.just(listOf(pic)) }
    }

    @Test
    fun `when the zero items loaded call is made, the items are not actually loaded more than once`() {
        b = ApodNetworkBoundaryCallback(api, dao, requestDates)
        b.onZeroItemsLoaded()
        b.onZeroItemsLoaded()
        verify(exactly = 1) { api.getApodList(any()) }
        verify(exactly = 1) { dao.insert(any()) }
    }

    @Test
    fun `the network state is set to success after a onZeroItemsLoaded call is complete`() {
        every { dao.insert(any()) } returns Unit
        b = ApodNetworkBoundaryCallback(api, dao, requestDates)
        b.onZeroItemsLoaded()
        assertThat(getValueForLiveData(b.networkCallState), equalTo<ApodCallState>(ApodCallState.Success))
    }

}
