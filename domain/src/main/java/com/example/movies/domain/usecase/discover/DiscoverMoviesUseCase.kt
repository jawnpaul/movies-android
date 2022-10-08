package com.example.movies.domain.usecase.discover

import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.DiscoverMovieRepository
import com.example.movies.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMoviesUseCase @Inject constructor(private val repository: DiscoverMovieRepository) :
    BaseUseCase<DiscoverMoviesUseCase.None, List<Movie>>() {
    class None

    override suspend fun run(params: None): Flow<Either<Failure, List<Movie>>> {
        return repository.discoverMovies()
    }
}
