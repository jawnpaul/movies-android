package com.example.movies.data.fakes

import com.example.movies.data.contract.local.DiscoverMovieCache
import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either

class FakeMovieDiscoverCache : DiscoverMovieCache {

    private val cache = LinkedHashMap<Long, MovieEntity>()

    override suspend fun getRecentDiscoveries(): Either<Failure, List<MovieEntity>> {
        return Either.Right(cache.values.toList())
    }

    override suspend fun saveMovieDiscoveries(entities: List<MovieEntity>): Either<Failure, Unit> {
        deleteRecentDiscoveries()
        entities.forEach {
            cache[it.id] = it
        }
        return Either.Right(Unit)
    }

    override suspend fun deleteRecentDiscoveries(): Either<Failure, Unit> {
        cache.clear()
        return Either.Right(Unit)
    }
}
