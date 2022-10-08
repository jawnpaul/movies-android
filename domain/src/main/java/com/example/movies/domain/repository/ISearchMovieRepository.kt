package com.example.movies.domain.repository

import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface ISearchMovieRepository {

    suspend fun searchMovies(query: String): Flow<Either<Failure, List<Movie>>>
}
