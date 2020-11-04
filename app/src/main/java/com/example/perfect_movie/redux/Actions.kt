package com.example.perfect_movie.redux

import com.example.perfect_movie.tmdb_api.TMDBMovie

sealed class Actions {
    class FetchingUpcomingItemsStartedAction
    class FetchingRatedItemsStartedAction
    data class FetchingUpcomingItemsSuccessAction(val items: List<TMDBMovie>)
    data class FetchingRatedItemsSuccessAction(val items: List<TMDBMovie>)
}