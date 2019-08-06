package com.aniketkadam.appod.mainscreen.data

import androidx.paging.PagedList
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.data.database.AstronomyPicDao
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.LocalDate

import javax.inject.Inject

class ApodNetworkBoundaryCallback @Inject constructor(
    private val api: ApodApi,
    private val dao: AstronomyPicDao,
    private val initialRequestDates: ApodRequestDates = ApodRequestConstructor().getDatesForNumItemsStartingToday(
        PREFETCH_DISTANCE
    )
) :
    PagedList.BoundaryCallback<AstronomyPic>() {

    private var loadingMoreItems: Disposable? = null

    private val initalLoad by lazy {
        // Get from the network and store in the database
        api.getApodList(initialRequestDates)
            .map(dao::insert)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        initalLoad
    }

    /**
     * We've reached the end, now need to load more pics
     */
    override fun onItemAtEndLoaded(itemAtEnd: AstronomyPic) {
        super.onItemAtEndLoaded(itemAtEnd)
        loadingMoreItems =
            api.getApodList(getNextItemsToLoad(itemAtEnd)) // dependent on loading items in the view in descending order!!
                .map(dao::insert)
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    /**
     * The correct date, assuming that you have items in descending order only and you're going to load items further in the past:
     * Is the date in the past from the last item here. That is, yesterday becomes the new end date.
     * The start date is PREFETCH_DISTANCE number of days before yesterday, hence the +1 for the start date.
     */
    private fun getNextItemsToLoad(itemAtEnd: AstronomyPic): ApodRequestDates {
        val dateOfLastItem = LocalDate.parse(itemAtEnd.date)
        return ApodRequestDates(
            dateOfLastItem.minusDays(PREFETCH_DISTANCE + 1).toString(),
            dateOfLastItem.minusDays(1).toString()
        )
    }
}