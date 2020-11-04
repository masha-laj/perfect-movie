package com.example.perfect_movie.redux

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Thunk
import kotlin.coroutines.CoroutineContext

class NetworkThunks(networkContext: CoroutineContext) {
    private val networkScope = CoroutineScope(networkContext)

    private fun repoForCategory(categoryId: MoviesCategory) = when (categoryId) {
        MoviesCategory.UPCOMING -> UpcomingItemRepository()
        MoviesCategory.RATED -> RatedItemRepository()
    }

    fun fetchItems(category: MoviesCategory, page: Int): Thunk<AppState> =
        { dispatch, getState, extraArg ->
            val repo = repoForCategory(category)
            networkScope.launch {
                when (category) {
                    MoviesCategory.UPCOMING -> dispatch(Actions.FetchingUpcomingItemsStartedAction())
                    MoviesCategory.RATED -> dispatch(Actions.FetchingRatedItemsStartedAction())
                }
                val result = repo.fetchItems(page)
                when (category) {
                    MoviesCategory.UPCOMING -> dispatch(
                        Actions.FetchingUpcomingItemsSuccessAction(
                            result
                        )
                    )
                    MoviesCategory.RATED -> dispatch(Actions.FetchingRatedItemsSuccessAction(result))
                }
            }
        }
}