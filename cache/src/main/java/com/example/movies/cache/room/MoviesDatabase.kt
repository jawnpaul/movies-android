package com.example.movies.cache.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.cache.entity.MovieCacheModel

@Database(
    entities = [MovieCacheModel::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "movie_database"
    }

    abstract val moviesDao: MoviesDao
}
