package com.example.movies.core.di

import com.example.movies.core.BuildConfig
import com.example.movies.data.repository.DiscoverMovieRepository
import com.example.movies.data.repository.SearchMovieRepository
import com.example.movies.domain.repository.IDiscoverMovieRepository
import com.example.movies.domain.repository.ISearchMovieRepository
import com.example.movies.remote.ApiFactory
import com.example.movies.remote.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {

    @get:Binds
    val DiscoverMovieRepository.discoverMovieRepository: IDiscoverMovieRepository

    @get:Binds
    val SearchMovieRepository.searchMovieRepository: ISearchMovieRepository

    companion object {
        @[Provides Singleton]
        fun provideApiService(): ApiService =
            ApiFactory.createApiService(BuildConfig.DEBUG, BuildConfig.API_KEY)
    }
}
