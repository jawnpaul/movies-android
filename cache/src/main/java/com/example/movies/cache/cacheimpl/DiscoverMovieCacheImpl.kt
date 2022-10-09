package com.example.movies.cache.cacheimpl

import com.example.movies.cache.mapper.MovieCacheModelMapper
import com.example.movies.cache.room.MoviesDao
import com.example.movies.data.contract.local.DiscoverMovieCache
import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import javax.inject.Inject

class DiscoverMovieCacheImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val mapper: MovieCacheModelMapper,
) :
    DiscoverMovieCache {
    override suspend fun getRecentDiscoveries(): Either<Failure, List<MovieEntity>> {
        val movieCacheModelList = moviesDao.getRecentDiscoveries()
        return Either.Right(mapper.mapToEntityList(movieCacheModelList))
    }

    override suspend fun saveMovieDiscoveries(entities: List<MovieEntity>): Either<Failure, Unit> {
        deleteRecentDiscoveries()

        val entityList = mapper.mapToModelList(entities)
        entityList.forEach {
            it.isDiscovered = true
        }
        moviesDao.insertMovies(entityList)
        return Either.Right(Unit)
    }

    override suspend fun deleteRecentDiscoveries(): Either<Failure, Unit> {
        moviesDao.deleteRecentDiscoveries()
        return Either.Right(Unit)
    }
}
