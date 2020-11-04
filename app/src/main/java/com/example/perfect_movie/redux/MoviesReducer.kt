package com.example.perfect_movie.redux

import org.reduxkotlin.ActionTypes
import org.reduxkotlin.Reducer

val reducer: Reducer<AppState> = { state: AppState, action ->
    when (action) {
        is ActionTypes.INIT -> {
            AppState.INITIAL_STATE
        }
        is Actions.FetchingUpcomingItemsStartedAction -> state.copy(isUpcomingLoadingItems = true)
        is Actions.FetchingRatedItemsStartedAction -> state.copy(isRatedLoadingItems = true)
        is Actions.FetchingUpcomingItemsSuccessAction -> state.copy(
            isUpcomingLoadingItems = false,
            upcomingMovies = action.items
        )
        is Actions.FetchingRatedItemsSuccessAction -> state.copy(
            isRatedLoadingItems = false,
            ratedMovies = action.items
        )
        else -> state
    }
}