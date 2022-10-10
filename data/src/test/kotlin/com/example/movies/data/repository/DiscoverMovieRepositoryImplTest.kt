package com.example.movies.data.repository

import com.example.movies.data.fakes.FakeMovieDiscoverCache
import com.example.movies.data.fakes.FakeMovieDiscoverRemote
import com.example.movies.data.mapper.MovieEntityMapper
import com.example.movies.data.model.DummyData
import com.example.movies.data.util.ResponseType
import com.example.movies.domain.functional.Either
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.DiscoverMovieRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DiscoverMovieRepositoryImplTest {

    private lateinit var movieEntityMapper: MovieEntityMapper
    private lateinit var recentMovieDiscoverCache: FakeMovieDiscoverCache
    private lateinit var movieDiscoverRemote: FakeMovieDiscoverRemote
    private lateinit var movieDiscoverRepository: DiscoverMovieRepository

    @Before
    fun setup() {
        movieEntityMapper = MovieEntityMapper()
        movieDiscoverRemote = FakeMovieDiscoverRemote()
        recentMovieDiscoverCache = FakeMovieDiscoverCache()
        movieDiscoverRepository =
            DiscoverMovieRepositoryImpl(
                movieDiscoverRemote,
                movieEntityMapper,
                recentMovieDiscoverCache
            )
    }

    @Test
    fun `check that discoverMovies returns data`() = runBlockingTest {
        movieDiscoverRemote.movieResponseType = ResponseType.DATA

        movieDiscoverRepository.discoverMovies().collect {
            assertThat(it).isInstanceOf(Either.Right::class.java)
            it as Either.Right
            assertThat(it.b).isNotEmpty()
        }
    }

    @Test
    fun `check that discoverMovies returns correct data`() = runBlockingTest {
        movieDiscoverRemote.movieResponseType = ResponseType.DATA
        movieDiscoverRepository.discoverMovies().collect {
            assertThat(it).isInstanceOf(Either.Right::class.java)
            it as Either.Right
            val movie: Movie = it.b.first()
            assertThat(movie.title).isEqualTo(DummyData.movie.title)
            assertThat(movie.originalTitle).isEqualTo(DummyData.movie.originalTitle)
            assertThat(movie.releaseDate).isEqualTo(DummyData.movie.releaseDate)
            assertThat(movie.posterPath).isEqualTo(DummyData.movie.posterPath)
            assertThat(movie.backdropPath).isEqualTo(DummyData.movie.backdropPath)
            assertThat(movie.popularity).isEqualTo(DummyData.movie.popularity)
            assertThat(movie.overview).isEqualTo(DummyData.movie.overview)
        }
    }

    @Test
    fun `check that discoverMovies saves recent data to cache`() = runBlockingTest {
        movieDiscoverRemote.movieResponseType = ResponseType.DATA
        movieDiscoverRepository.discoverMovies().collect()
        movieDiscoverRepository.getRecentDiscoveries().collect {
            assertThat(it).isInstanceOf(Either.Right::class.java)
            it as Either.Right
            assertThat(it.b).isNotEmpty()
        }
    }

    @Test
    fun `check that discoverMovies() returns empty on no data`() = runBlockingTest {
        movieDiscoverRemote.movieResponseType = ResponseType.EMPTY_DATA
        movieDiscoverRepository.discoverMovies().collect {
            assertThat(it).isInstanceOf(Either.Right::class.java)
            it as Either.Right
            assertThat(it.b).isEmpty()
        }
    }

    @Test
    fun `check that discoverMovies() returns error`() = runBlockingTest {
        movieDiscoverRemote.movieResponseType = ResponseType.ERROR
        movieDiscoverRepository.discoverMovies().collect {
            assertThat(it).isInstanceOf(Either.Left::class.java)
            it as Either.Left
            assertThat(it.a).isNotNull()
        }
    }
}
