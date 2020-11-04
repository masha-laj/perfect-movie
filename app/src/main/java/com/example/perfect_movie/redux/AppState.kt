package com.example.perfect_movie.redux

import com.example.perfect_movie.BuildConfig
import com.example.perfect_movie.tmdb_api.TMDBApi
import com.example.perfect_movie.tmdb_api.TMDBMovie

data class AppState(
    val isUpcomingLoadingItems: Boolean = false,
    val isRatedLoadingItems: Boolean = false,
    val upcomingMovies: List<TMDBMovie> = listOf(),
    val ratedMovies: List<TMDBMovie> = listOf()
) {
    companion object {
        val INITIAL_STATE = AppState()
    }
}

enum class MoviesCategory(val displayName: String) {
    UPCOMING("Upcoming"),
    RATED("Rated");

    companion object {
        val displayNameList by lazy {
            values().map { it.displayName }
        }

        fun fromOrdinal(ordinal: Int) = values()[ordinal]

        fun fromDisplayName(displayName: String) = values().find { it.displayName == displayName }
    }
}

val tmdbClient = TMDBApi(BuildConfig.TMDB_API_KEY)