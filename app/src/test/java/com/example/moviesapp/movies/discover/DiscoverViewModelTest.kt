package com.example.moviesapp.movies.discover

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.discover.DiscoverMoviesUseCase
import com.example.movies.domain.usecase.discover.RecentDiscoverMoviesUseCase
import com.example.moviesapp.data.DummyData
import com.example.moviesapp.mapper.MoviePresentationMapper
import com.example.moviesapp.util.UnitTest
import com.example.moviesapp.util.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DiscoverViewModelTest : UnitTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var discoverMoviesUseCase: DiscoverMoviesUseCase

    @MockK
    private lateinit var recentDiscoverMoviesUseCase: RecentDiscoverMoviesUseCase

    private lateinit var movies: List<Movie>

    private lateinit var movieDiscoverViewModel: DiscoverViewModel
    private lateinit var moviePresentationMapper: MoviePresentationMapper

    @Before
    fun setup() {
        movies = DummyData.movies
        moviePresentationMapper = MoviePresentationMapper()
        movieDiscoverViewModel = DiscoverViewModel(
            discoverMoviesUseCase,
            recentDiscoverMoviesUseCase, moviePresentationMapper
        )
    }

    @Test
    fun `check that getRecentDiscoveries returns correct data`() {
        // fakeMovieDiscoverRepository.movieListResponseType = ResponseType.DATA

        every { recentDiscoverMoviesUseCase(any(), any(), any()) }.answers {
            thirdArg<(Either<Failure, List<Movie>>) -> Unit>()(Either.Right(movies))
        }

        movieDiscoverViewModel.getRecentDiscoveries()
        val res = movieDiscoverViewModel.movieListView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.movies)
            .isEqualTo(movies.map(moviePresentationMapper::mapToPresentation))
    }

    @Test
    fun `check that getRecentDiscoveries returns empty data`() {
        // fakeMovieDiscoverRepository.movieListResponseType = ResponseType.EMPTY_DATA

        every { recentDiscoverMoviesUseCase(any(), any(), any()) }.answers {
            thirdArg<(Either<Failure, List<Movie>>) -> Unit>()(Either.Right(emptyList()))
        }

        movieDiscoverViewModel.getRecentDiscoveries()
        val res = movieDiscoverViewModel.movieListView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.isEmpty).isTrue()
    }

    @Test
    fun `check that getRecentDiscoveries returns error`() {
        // fakeMovieDiscoverRepository.movieListResponseType = ResponseType.ERROR
        every { recentDiscoverMoviesUseCase(any(), any(), any()) }.answers {
            thirdArg<(Either<Failure, List<Movie>>) -> Unit>()(Either.Left(Failure.ServerError))
        }

        movieDiscoverViewModel.getRecentDiscoveries()
        val res = movieDiscoverViewModel.movieListView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNotNull()
        assertThat(res.errorMessage).isNotEqualTo("Something went wrong.")
    }

    @Test
    fun `check that discoverMovies returns correct data`() {
        // fakeMovieDiscoverRepository.movieListResponseType = ResponseType.DATA
        every { discoverMoviesUseCase(any(), any(), any()) }.answers {
            thirdArg<(Either<Failure, List<Movie>>) -> Unit>()(
                Either.Right(movies)
            )
        }
        movieDiscoverViewModel.discoverMovies()
        val res = movieDiscoverViewModel.movieListView.getOrAwaitValueTest()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.movies)
            .isEqualTo(DummyData.movies.map(moviePresentationMapper::mapToPresentation))
    }

    @Test
    fun `check that discoverMovies returns empty data`() {
        // fakeMovieDiscoverRepository.movieListResponseType = ResponseType.EMPTY_DATA

        every { discoverMoviesUseCase(any(), any(), any()) }.answers {
            thirdArg<(Either<Failure, List<Movie>>) -> Unit>()(
                Either.Right(emptyList())
            )
        }

        movieDiscoverViewModel.discoverMovies()
        val res = movieDiscoverViewModel.movieListView.getOrAwaitValueTest()
        assertThat(res.isEmpty).isTrue()
    }

    @Test
    fun `check that discoverMovies returns error`() {

        every { discoverMoviesUseCase(any(), any(), any()) }.answers {
            thirdArg<(Either<Failure, List<Movie>>) -> Unit>()(
                Either.Left(Failure.ServerError)
            )
        }

        movieDiscoverViewModel.discoverMovies()
        val res = movieDiscoverViewModel.movieListView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNotNull()
        assertThat(res.errorMessage).isNotEqualTo("Something went wrong.")
    }
}
