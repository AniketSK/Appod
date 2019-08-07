package com.aniketkadam.appod.data.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aniketkadam.appod.data.AstronomyPic

@Dao
interface AstronomyPicDao {
    @Insert
    fun insert(picList: List<AstronomyPic>)

    @Query("SELECT * from AstronomyPic")
    fun getAstronomyPics(): LiveData<List<AstronomyPic>>

    @Query("SELECT * FROM AstronomyPic ORDER BY date(date) DESC")
    fun getAstronomyPicsDataSource(): DataSource.Factory<Int, AstronomyPic>
}