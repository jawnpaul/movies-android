package com.example.movies.data.repository

import com.example.movies.data.contract.remote.ISearchMovieRemote
import com.example.movies.data.mapper.MovieEntityMapper
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.ISearchMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMovieRepository @Inject constructor(
    private val iSearchMovieRemote: ISearchMovieRemote,
    private val mapper: MovieEntityMapper,
) :
    ISearchMovieRepository {
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
