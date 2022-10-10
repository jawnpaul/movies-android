package com.example.movies.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.cache.entity.MovieCacheModel

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieCacheModelList: List<MovieCacheModel>)

    @Query("SELECT * FROM movies_table WHERE isDiscovered = 1")
    suspend fun getRecentDiscoveries(): List<MovieCacheModel>

    @Query("DELETE FROM movies_table WHERE isDiscovered = 1")
    suspend fun deleteRecentDiscoveries()
}
