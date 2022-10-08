package com.example.movies.core.di

import com.example.movies.data.repository.DiscoverMovieRepositoryImpl
import com.example.movies.data.repository.SearchMovieRepositoryImpl
import com.example.movies.domain.repository.DiscoverMovieRepository
import com.example.movies.domain.repository.SearchMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @get:Binds
    val DiscoverMovieRepositoryImpl.discoverMovieRepositoryImpl: DiscoverMovieRepository

    @get:Binds
    val SearchMovieRepositoryImpl.searchMovieRepositoryImpl: SearchMovieRepository
}
