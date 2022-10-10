package com.example.moviesapp.data

import com.example.movies.domain.model.Movie
import com.example.moviesapp.model.MoviePresentation

object DummyData {

    val moviePresentation = MoviePresentation(
        id = 1,
        adult = "No",
        backdropPath = "https://image.tmdb.org/t/p/w500/yuXFCFKuajuuPwcGD1LdHL8bnye.jpg",
        posterPath = "https://image.tmdb.org/t/p/w500/yuXFCFKuajuuPwcGD1LdHL8bnye.jpg",
        originalTitle = "Suzi Q",
        overview = "Story of trailblazing American rock singer-songwriter Suzi Quatro, who helped redefine the role of women in rock 'n' roll when she broke out in 1973.",
        popularity = "1.9",
        title = "Suzi Q",
        releaseDate = "2019-08-15",
        voteAverage = "8.0",
        voteCount = 3
    )

    val movie = Movie(
        id = 1,
        adult = false,
        backdropPath = "https://image.tmdb.org/t/p/w500/yuXFCFKuajuuPwcGD1LdHL8bnye.jpg",
        posterPath = "https://image.tmdb.org/t/p/w500/yuXFCFKuajuuPwcGD1LdHL8bnye.jpg",
        originalTitle = "Suzi Q",
        overview = "Story of trailblazing American rock singer-songwriter Suzi Quatro, who helped redefine the role of women in rock 'n' roll when she broke out in 1973.",
        popularity = 1.9,
        title = "Suzi Q",
        releaseDate = "2019-08-15",
        voteAverage = 8.0,
        voteCount = 3
    )

    val movies = listOf(movie)
}
