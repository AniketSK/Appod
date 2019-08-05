package com.aniketkadam.appod.mainscreen.data

import androidx.paging.PagedList
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.data.database.AstronomyPicDao
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApodNetworkBoundaryCallback @Inject constructor(
    private val api: ApodApi,
    private val dao: AstronomyPicDao,
    private val initialRequestDates: ApodRequestDates = ApodRequestConstructor().getDatesForNumItemsStartingToday(
        PREFETCH_DISTANCE
    )
) :
    PagedList.BoundaryCallback<AstronomyPic>() {

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


}