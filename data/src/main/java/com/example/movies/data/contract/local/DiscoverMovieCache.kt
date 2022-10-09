package com.example.movies.data.contract.local

import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either

interface DiscoverMovieCache {

    suspend fun getRecentDiscoveries(): Either<Failure, List<MovieEntity>>

    suspend fun saveMovieDiscoveries(entities: List<MovieEntity>): Either<Failure, Unit>

    suspend fun deleteRecentDiscoveries(): Either<Failure, Unit>
}
