package com.mobg5.filmbox.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultsMovies(
    @Json(name="id") val id: Int,
    @Json(name="original_title") val originalTitle: String?,
    @Json(name="original_language") val originalLanguage: String?,
    @Json(name="release_date") val releaseDate: String?,
    @Json(name="title") val title: String?,
    @Json(name="overview") val overview: String?,
    @Json(name="backdrop_path") val backdropPath: String?,
    @Json(name="poster_path") val posterPath: String?,
    @Json(name="popularity") val popularity: Double?,
    @Json(name="vote_average") val voteAverage: Double?,
    @Json(name="vote_count") val voteCount: Int?,
) : Parcelable