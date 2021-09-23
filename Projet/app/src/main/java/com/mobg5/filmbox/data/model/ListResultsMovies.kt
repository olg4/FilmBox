package com.mobg5.filmbox.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListResultsMovies (
    val page: Int,
    val results: List<ResultsMovies>,
    @Json(name="total_pages") val totalPages: Int,
    @Json(name="total_results") val totalResults: Int
) : Parcelable
