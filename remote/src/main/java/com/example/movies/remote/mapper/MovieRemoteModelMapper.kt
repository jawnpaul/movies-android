package com.example.movies.remote.mapper

import com.example.movies.data.model.MovieEntity
import com.example.movies.remote.mapper.base.RemoteModelMapper
import com.example.movies.remote.model.MovieRemoteModel
import javax.inject.Inject

class MovieRemoteModelMapper @Inject constructor() :
    RemoteModelMapper<MovieRemoteModel, MovieEntity> {
    private val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/w500"
    override fun mapFromModel(model: MovieRemoteModel): MovieEntity {
        return MovieEntity(
            id = model.id,
            adult = model.adult,
            backdropPath = IMAGE_BASE_URL.plus(model.backdrop_path),
            posterPath = IMAGE_BASE_URL.plus(model.poster_path),
            popularity = model.popularity,
            originalTitle = model.original_title,
            title = model.title,
            overview = model.overview,
            releaseDate = model.release_date,
            voteAverage = model.vote_average,
            voteCount = model.vote_count
        )
    }
}
