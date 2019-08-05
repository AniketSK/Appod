package com.aniketkadam.appod.mainscreen.data

import androidx.paging.PagedList
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.AstronomyPic
import javax.inject.Inject

class ApodNetworkBoundaryCallback @Inject constructor(private val api: ApodApi) :
    PagedList.BoundaryCallback<AstronomyPic>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        // Make the initial call to get all the data.
//        api.getApodList()
    }
}