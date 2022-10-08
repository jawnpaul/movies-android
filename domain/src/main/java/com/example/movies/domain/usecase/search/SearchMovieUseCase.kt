package com.example.movies.domain.usecase.search

import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.SearchMovieRepository
import com.example.movies.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val repository: SearchMovieRepository) :
    BaseUseCase<String, List<Movie>>() {
    override suspend fun run(params: String): Flow<Either<Failure, List<Movie>>> {
        return repository.searchMovies(params)
    }
}
