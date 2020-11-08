package com.example.perfect_movie.redux

import com.example.perfect_movie.tmdb_api.TMDBMovie

interface ItemRepository {
    suspend fun fetchItems(page: Int): List<TMDBMovie>
}

class UpcomingItemRepository() : ItemRepository {

    override suspend fun fetchItems(page: Int): List<TMDBMovie> {
        return tmdbClient.getUpcoming(page)
    }

}

class RatedItemRepository() : ItemRepository {

    override suspend fun fetchItems(page: Int): List<TMDBMovie> {
        return tmdbClient.getRated(page)
    }
}