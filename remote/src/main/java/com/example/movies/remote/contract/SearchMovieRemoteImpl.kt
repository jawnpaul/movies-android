package com.example.movies.remote.contract

import com.example.movies.data.contract.remote.SearchMovieRemote
import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import com.example.movies.remote.ApiService
import com.example.movies.remote.mapper.MovieRemoteModelMapper
import javax.inject.Inject

class SearchMovieRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: MovieRemoteModelMapper,
) : SearchMovieRemote {
    override suspend fun searchMovies(query: String): Either<Failure, List<MovieEntity>> {
        return try {
            val res = apiService.searchMovies(query)
            when (res.isSuccessful) {
                true -> {
                    res.body()?.let {
                        Either.Right(mapper.mapFromModelList(it.results))
                    } ?: Either.Right(emptyList())
                }
                false -> {
                    Either.Left(Failure.ServerError)
                }
            }
        } catch (e: Exception) {
            Either.Left(Failure.ServerError)
        }
    }
}
