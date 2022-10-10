package com.example.movies.data.fakes

import com.example.movies.data.contract.remote.DiscoverMovieRemote
import com.example.movies.data.model.DummyData
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.util.ResponseType
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either

class FakeMovieDiscoverRemote : DiscoverMovieRemote {

    var movieResult: Either<Failure, List<MovieEntity>> =
        Either.Right(listOf(DummyData.movieEntity))
    var movieResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            movieResult = makeResponse(value)
        }

    private fun makeResponse(type: ResponseType): Either<Failure, List<MovieEntity>> {
        return when (type) {
            ResponseType.DATA -> movieResult
            ResponseType.EMPTY_DATA -> Either.Right(emptyList())
            ResponseType.ERROR -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun discoverMovies(): Either<Failure, List<MovieEntity>> {
        return movieResult
    }
}
