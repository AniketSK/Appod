package com.aniketkadam.appod.data.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aniketkadam.appod.data.AstronomyPic
import io.reactivex.Maybe

@Dao
interface AstronomyPicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(picList: List<AstronomyPic>)

    @Query("SELECT * from AstronomyPic")
    fun getAstronomyPics(): LiveData<List<AstronomyPic>>

    @Query("SELECT * FROM AstronomyPic ORDER BY date(date) DESC")
    fun getAstronomyPicsDataSource(): DataSource.Factory<Int, AstronomyPic>

    @Query("SELECT * from AstronomyPic ORDER BY date(date) DESC LIMIT 1")
    fun getLatestAstronomyPic(): Maybe<AstronomyPic>

    @Query("UPDATE AstronomyPic SET bookmark = :isBookmark WHERE date =:id")
    fun setIsBookmarkedAstronomyPic(id: String, isBookmark: Boolean)
}