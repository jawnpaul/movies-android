package com.example.moviesapp.model.state

import com.example.moviesapp.model.MoviePresentation

data class MovieListView(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val movies: List<MoviePresentation>? = null,
)
