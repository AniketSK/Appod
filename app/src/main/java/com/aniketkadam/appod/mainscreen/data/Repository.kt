package com.aniketkadam.appod.mainscreen.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aniketkadam.appod.data.ApodApi
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.data.database.AstronomyPicDao
import javax.inject.Inject

const val PREFETCH_DISTANCE = 50
const val PAGE_SIZE = PREFETCH_DISTANCE

class Repository @Inject constructor(private val dao: AstronomyPicDao, private val apodApi: ApodApi) {

    fun getApodList(): RepoResult {

        val localSource = dao.getAstronomyPicsDataSource()

        val config = PagedList.Config.Builder().setPrefetchDistance(PREFETCH_DISTANCE).setPageSize(PAGE_SIZE)
            .setMaxSize(PAGE_SIZE + PREFETCH_DISTANCE * 2) // this is the minimum it can be
            .setEnablePlaceholders(true)
            .build()

        val boundaryCallback = ApodNetworkBoundaryCallback(apodApi, dao)


        return RepoResult(
            LivePagedListBuilder(localSource, config)
                .setBoundaryCallback(boundaryCallback)
                .build(), boundaryCallback.networkCallState
        )
    }

    fun getLatestAstronomyPic() = dao.getLatestAstronomyPic()
    fun getApodsFromRemote(apodRequestDates: ApodRequestDates) = apodApi.getApodList(apodRequestDates)
    fun saveData(list: List<AstronomyPic>) = dao.insert(list)

}

data class RepoResult(val data: LiveData<PagedList<AstronomyPic>>, val networkState: LiveData<ApodCallState>)
