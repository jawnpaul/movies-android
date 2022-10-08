package com.example.movies.remote.model.response

import com.example.movies.remote.model.MovieRemoteModel

data class MovieResponse(
    val page: Int,
    val results: List<MovieRemoteModel>,
)
