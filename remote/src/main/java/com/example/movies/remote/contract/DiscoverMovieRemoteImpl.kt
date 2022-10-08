package com.example.movies.remote.contract

import com.example.movies.data.contract.remote.DiscoverMovieRemote
import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import com.example.movies.remote.ApiService
import com.example.movies.remote.mapper.MovieRemoteModelMapper
import javax.inject.Inject

class DiscoverMovieRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: MovieRemoteModelMapper,
) : DiscoverMovieRemote {
    override suspend fun discoverMovies(): Either<Failure, List<MovieEntity>> {
        return try {
            val res = apiService.discoverMovies()
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
