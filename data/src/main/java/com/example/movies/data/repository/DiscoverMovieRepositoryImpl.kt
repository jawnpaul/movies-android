package com.example.movies.data.repository

import com.example.movies.data.contract.remote.DiscoverMovieRemote
import com.example.movies.data.mapper.MovieEntityMapper
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.DiscoverMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiscoverMovieRepositoryImpl @Inject constructor(
    private val iDiscoverMovieRemote: DiscoverMovieRemote,
    private val mapper: MovieEntityMapper,
) :
    DiscoverMovieRepository {
    override suspend fun discoverMovies(): Flow<Either<Failure, List<Movie>>> = flow {
        when (val movies = iDiscoverMovieRemote.discoverMovies()) {
            is Either.Left -> {
                //
                emit(Either.Left(movies.a))
            }
            is Either.Right -> {
                // save to local db
                emit(Either.Right(mapper.mapToDomainList(movies.b)))
            }
        }
    }
}
