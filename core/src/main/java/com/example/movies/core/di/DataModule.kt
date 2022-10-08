package com.example.movies.core.di

import com.example.movies.data.repository.DiscoverMovieRepository
import com.example.movies.data.repository.SearchMovieRepository
import com.example.movies.domain.repository.IDiscoverMovieRepository
import com.example.movies.domain.repository.ISearchMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @get:Binds
    val DiscoverMovieRepository.discoverMovieRepository: IDiscoverMovieRepository

    @get:Binds
    val SearchMovieRepository.searchMovieRepository: ISearchMovieRepository
}
