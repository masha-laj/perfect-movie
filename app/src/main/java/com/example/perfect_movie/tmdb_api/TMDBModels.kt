package com.example.perfect_movie.tmdb_api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TMDBMovie(
    var id: Int,
    var poster_path: String,
    var adult: Boolean,
    var overview: String,
    var release_date: String,
    var genre_ids: List<Int>,
    var original_title: String,
    var original_language: String,
    var title: String,
    var backdrop_path: String?,
    var popularity: Float,
    var vote_count: Int,
    var video: Boolean,
    var vote_average: Float
) : Parcelable

data class TMDBDates(var maximum: String, var minimum: String)

data class TMDBUpcomingResponse(
    var page: Int,
    var results: List<TMDBMovie>,
    var dates: TMDBDates,
    var total_pages: Int,
    var total_results: Int
)