package com.example.movies.cache.di

import android.content.Context
import androidx.room.Room
import com.example.movies.cache.cacheimpl.DiscoverMovieCacheImpl
import com.example.movies.cache.room.MoviesDao
import com.example.movies.cache.room.MoviesDatabase
import com.example.movies.data.contract.local.DiscoverMovieCache
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CacheModule {

    @get:Binds
    val DiscoverMovieCacheImpl.discoverMovieCacheImpl: DiscoverMovieCache

    companion object {
        @[Provides Singleton]
        fun provideDataBase(@ApplicationContext context: Context): MoviesDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java,
                MoviesDatabase.DB_NAME
            ).fallbackToDestructiveMigration().build()
        }

        @[Provides Singleton]
        fun provideMoviesDao(movieDatabase: MoviesDatabase): MoviesDao = movieDatabase.moviesDao
    }
}
