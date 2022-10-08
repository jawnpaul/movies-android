package com.example.movies.data.repository

import com.example.movies.data.contract.remote.SearchMovieRemote
import com.example.movies.data.mapper.MovieEntityMapper
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.SearchMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val iSearchMovieRemote: SearchMovieRemote,
    private val mapper: MovieEntityMapper,
) :
    SearchMovieRepository {
    override suspend fun searchMovies(query: String): Flow<Either<Failure, List<Movie>>> = flow {
        when (val movies = iSearchMovieRemote.searchMovies(query)) {
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
