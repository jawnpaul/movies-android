package com.example.moviesapp.movies.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.MoviePresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor() : ViewModel() {

    private val _movie = MutableLiveData<MoviePresentation>()
    val movie: LiveData<MoviePresentation>
        get() = _movie

    fun setMovieDetail(moviePresentation: MoviePresentation) {
        _movie.value = moviePresentation
    }
}
