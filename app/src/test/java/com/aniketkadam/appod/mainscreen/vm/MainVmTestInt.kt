package com.aniketkadam.appod.mainscreen.vm

import com.aniketkadam.appod.RxImmediateSchedulerRule
import com.aniketkadam.appod.data.emptyAstronomyPic
import com.aniketkadam.appod.mainscreen.data.ApodRequestDates
import com.aniketkadam.appod.mainscreen.data.PREFETCH_DISTANCE
import com.aniketkadam.appod.mainscreen.data.RepoResult
import com.aniketkadam.appod.mainscreen.data.Repository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.joda.time.LocalDate
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainVmTestInt {

    @MockK
    lateinit var repository: Repository
    @MockK(relaxed = true)
    lateinit var repoResult: RepoResult
    lateinit var mainVm: MainVm

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { repository.getApodList() } returns repoResult
        every { repository.getApodsFromRemote(any()) } returns Single.just(listOf(emptyAstronomyPic()))
        every { repository.saveData(any()) } returns Unit
        mainVm = MainVm(repository)
    }

    @Test
    fun `swipe refresh works when the date is in the past`() {
        every { repository.getLatestAstronomyPic() } returns Maybe.just(emptyAstronomyPic().copy(date = "2019-08-01"))
        mainVm.refreshState.test()
        mainVm.sendRefreshEvent()
        verify(exactly = 1) { repository.getApodsFromRemote(any()) }
        verify(exactly = 1) { repository.saveData(any()) }
    }

    @Test
    fun `swipe refresh does nothing if we're up to date`() {
        every { repository.getLatestAstronomyPic() } returns Maybe.just(emptyAstronomyPic().copy(date = LocalDate.now().toString()))
        mainVm.refreshState.test()

        verify(exactly = 0) { repository.getApodsFromRemote(any()) }
    }

    @Test
    fun `swipe refresh works when the database is empty`() {

        every { repository.getLatestAstronomyPic() } returns Maybe.empty()
        val t = mainVm.refreshState.test()
        mainVm.sendRefreshEvent()
        verify(exactly = 1) { repository.getApodsFromRemote(any()) }
        verify(exactly = 1) { repository.saveData(any()) }
        t.assertNotComplete()
    }

    @Test
    fun `swipe refresh requests correct dates when it has to get just today's apod`() {
        val yesterday = LocalDate.now().minusDays(1).toString()
        val today = LocalDate.now().toString()
        every { repository.getLatestAstronomyPic() } returns Maybe.just(emptyAstronomyPic().copy(date = yesterday))
        mainVm.refreshState.test()
        mainVm.sendRefreshEvent()
        verify(exactly = 1) { repository.getApodsFromRemote(ApodRequestDates(yesterday, today)) }
    }

    @Test
    fun `swipe refresh requests correct dates when the database is empty`() {
        val today = LocalDate.now().toString()
        val startDate = LocalDate.now().minusDays(PREFETCH_DISTANCE).toString()
        every { repository.getLatestAstronomyPic() } returns Maybe.empty()
        mainVm.refreshState.test()
        mainVm.sendRefreshEvent()
        verify(exactly = 1) { repository.getApodsFromRemote(ApodRequestDates(startDate, today)) }
    }


    @Test
    fun `loading is shown when the swipe refresh is initiated`() {
        every { repository.getLatestAstronomyPic() } returns Maybe.just(emptyAstronomyPic().copy(date = "2019-08-01"))
        val q: TestObserver<RefreshLce> = mainVm.refreshState.test().assertSubscribed().assertNotComplete()
        q.assertNoValues()
        mainVm.sendRefreshEvent()
        q.assertValues(RefreshLce.Loading, RefreshLce.Success).assertNotComplete()
    }

}