package com.example.movies.data.contract.remote

import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either

interface SearchMovieRemote {

    suspend fun searchMovies(query: String): Either<Failure, List<MovieEntity>>
}
