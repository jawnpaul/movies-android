package com.example.movies.core.di

import com.example.movies.core.BuildConfig
import com.example.movies.data.contract.remote.DiscoverMovieRemote
import com.example.movies.data.contract.remote.SearchMovieRemote
import com.example.movies.remote.ApiFactory
import com.example.movies.remote.ApiService
import com.example.movies.remote.contract.DiscoverMovieRemoteImpl
import com.example.movies.remote.contract.SearchMovieRemoteImpl
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
    val DiscoverMovieRemoteImpl.discoverMovieRemoteImpl: DiscoverMovieRemote

    @get:Binds
    val SearchMovieRemoteImpl.searchMovieRemoteImpl: SearchMovieRemote

    companion object {
        @[Provides Singleton]
        fun provideApiService(): ApiService =
            ApiFactory.createApiService(BuildConfig.DEBUG, BuildConfig.API_KEY)
    }
}
