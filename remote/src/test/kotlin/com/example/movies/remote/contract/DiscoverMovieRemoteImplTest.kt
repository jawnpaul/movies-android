package com.example.movies.remote.contract

import com.example.movies.data.contract.remote.DiscoverMovieRemote
import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.functional.Either
import com.example.movies.remote.mapper.MovieRemoteModelMapper
import com.example.movies.remote.utils.MOVIE_DISCOVER_REQUEST_PATH
import com.example.movies.remote.utils.MovieRequestDispatcher
import com.example.movies.remote.utils.makeTestApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class DiscoverMovieRemoteImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var movieDiscoverRemote: DiscoverMovieRemote
    private val movieRemoteModelMapper = MovieRemoteModelMapper()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MovieRequestDispatcher().RequestDispatcher()
        mockWebServer.start()
        movieDiscoverRemote =
            DiscoverMovieRemoteImpl(makeTestApiService(mockWebServer), movieRemoteModelMapper)
    }

    @Test
    fun `check that discoverMovies returns movies list`() = runBlocking {
        val movieResult: Either<Failure, List<MovieEntity>> = movieDiscoverRemote.discoverMovies()
        assertThat(movieResult).isInstanceOf(Either.Right::class.java)
        movieResult as Either.Right
        val movies: List<MovieEntity> = movieResult.b
        assertThat(movies).isNotEmpty()
    }

    @Test
    fun `check that discoverMovies returns correct data`() = runBlocking {
        val movieResult: Either<Failure, List<MovieEntity>> = movieDiscoverRemote.discoverMovies()
        assertThat(movieResult).isInstanceOf(Either.Right::class.java)
        movieResult as Either.Right
        val movies: List<MovieEntity> = movieResult.b
        val firstMovie: MovieEntity = movies.first()
        assertThat(firstMovie.adult).isFalse()
        assertThat(firstMovie.title).isEqualTo("Desire")
        assertThat(firstMovie.originalTitle).isEqualTo("Q")
        assertThat(firstMovie.releaseDate).isEqualTo("2011-09-14")
        assertThat(firstMovie.posterPath).isEqualTo(movieRemoteModelMapper.IMAGE_BASE_URL.plus("/fNSXMHxe1i4VOVJxxwJyGzv2ZTG.jpg"))
        assertThat(firstMovie.backdropPath).isEqualTo(movieRemoteModelMapper.IMAGE_BASE_URL.plus("/q0eXltiQKRqD3qMdN3OC55O06Dy.jpg"))
        assertThat(firstMovie.popularity).isEqualTo(21.799)
    }

    @Test
    fun `check that calling discoverMovies makes request to correct path`() = runBlocking {
        movieDiscoverRemote.discoverMovies()
        assertThat(MOVIE_DISCOVER_REQUEST_PATH)
            .isEqualTo(mockWebServer.takeRequest().path)
    }

    @Test
    fun `check that calling discoverMovies makes a GET request`() = runBlocking {
        movieDiscoverRemote.discoverMovies()
        assertThat("GET $MOVIE_DISCOVER_REQUEST_PATH HTTP/1.1")
            .isEqualTo(mockWebServer.takeRequest().requestLine)
    }

    @Test
    fun `check that discoverMovies returns empty list when no movie is found`() =
        runBlocking {
            mockWebServer.dispatcher = MovieRequestDispatcher().EmptyResponseRequestDispatcher()
            val movieResult: Either<Failure, List<MovieEntity>> =
                movieDiscoverRemote.discoverMovies()
            movieResult as Either.Right
            val movies: List<MovieEntity> = movieResult.b
            assertThat(movies).isEmpty()
        }

    @Test
    fun `check that discoverMovies returns proper error on bad response from server`() =
        runBlocking {
            mockWebServer.dispatcher = MovieRequestDispatcher().BadRequestDispatcher()
            val movieResult: Either<Failure, List<MovieEntity>> =
                movieDiscoverRemote.discoverMovies()
            assertThat(movieResult).isInstanceOf(Either.Left::class.java)
            movieResult as Either.Left
            assertThat(movieResult.a).isInstanceOf(Failure.ServerError::class.java)
        }

    @Test
    fun `check that discoverMovies returns proper error on server error`() =
        runBlocking {
            mockWebServer.dispatcher = MovieRequestDispatcher().ErrorRequestDispatcher()
            val movieResult: Either<Failure, List<MovieEntity>> =
                movieDiscoverRemote.discoverMovies()
            assertThat(movieResult).isInstanceOf(Either.Left::class.java)
            movieResult as Either.Left
            assertThat(movieResult.a).isInstanceOf(Failure.ServerError::class.java)
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
