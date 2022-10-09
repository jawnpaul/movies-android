package com.example.moviesapp.movies.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.domain.exception.Failure
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.discover.DiscoverMoviesUseCase
import com.example.movies.domain.usecase.discover.RecentDiscoverMoviesUseCase
import com.example.moviesapp.mapper.MoviePresentationMapper
import com.example.moviesapp.model.state.MovieListView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val discoverMoviesUseCase: DiscoverMoviesUseCase,
    private val moviePresentationMapper: MoviePresentationMapper,
    private val recentDiscoverMoviesUseCase: RecentDiscoverMoviesUseCase,
) :
    ViewModel() {

    private val job = Job()

    private val _movieListView = MutableLiveData<MovieListView>()
    val movieListView: LiveData<MovieListView>
        get() = _movieListView

    private val _showBottomNavBar = MutableLiveData(true)
    val showBottomNavBar: LiveData<Boolean> get() = _showBottomNavBar

    fun discoverMovies() {
        _movieListView.value = MovieListView(loading = true)
        discoverMoviesUseCase(job, DiscoverMoviesUseCase.None()) {
            it.fold(
                ::handleDiscoverFailure,
                ::handleDiscoverSuccess
            )
        }
    }

    private fun handleDiscoverFailure(failure: Failure) {
        getRecentDiscoveries()
        when (failure) {
            is Failure.ServerError -> {
                _movieListView.value = MovieListView(loading = false, errorMessage = "Server Error")
            }
            else -> {
                _movieListView.value =
                    MovieListView(loading = false, errorMessage = "Something went wrong.")
            }
        }
    }

    private fun handleDiscoverSuccess(movies: List<Movie>) {
        if (movies.isEmpty()) {
            _movieListView.value = MovieListView(loading = false, isEmpty = true)
        } else {
            val presentationList = movies.map(moviePresentationMapper::mapToPresentation)
            _movieListView.value = MovieListView(loading = false, movies = presentationList)
        }
    }

    private fun getRecentDiscoveries() {
        recentDiscoverMoviesUseCase(job, RecentDiscoverMoviesUseCase.None()) {
            it.fold(
                ::handleDiscoverFailure,
                ::handleDiscoverSuccess
            )
        }
    }

    fun showBottomNavBar(value: Boolean) {
        _showBottomNavBar.value = value
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
