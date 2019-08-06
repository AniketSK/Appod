package com.aniketkadam.appod.mainscreen.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.data.database.AstronomyPicDao
import javax.inject.Inject

const val PREFETCH_DISTANCE = 50

class Repository @Inject constructor(private val dao: AstronomyPicDao, private val apodApi: ApodApi) {

    fun getApodList(): RepoResult {

        val localSource = dao.getAstronomyPicsDataSource()

        val config = PagedList.Config.Builder().setPrefetchDistance(PREFETCH_DISTANCE).setPageSize(PREFETCH_DISTANCE)
            .setMaxSize(PREFETCH_DISTANCE * 2)
            .build()

        val boundaryCallback = ApodNetworkBoundaryCallback(apodApi, dao)


        return RepoResult(
            LivePagedListBuilder(localSource, config)
                .setBoundaryCallback(boundaryCallback)
                .build(), boundaryCallback.networkCallState
        )
    }
}

data class RepoResult(val data: LiveData<PagedList<AstronomyPic>>, val networkState: LiveData<ApodCallState>)