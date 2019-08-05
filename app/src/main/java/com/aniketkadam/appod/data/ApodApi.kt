package com.aniketkadam.appod.data

import com.aniketkadam.appod.BuildConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {

    // URL reference https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2006-08-5&end_date=2006-08-12
    @GET("planetary/apod")
    fun getApodList(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = BuildConfig.APOD_API_KEY
    ): Single<List<AstronomyPic>>
}