package com.example.perfect_movie.tmdb_api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class TMDBApi constructor(private val apikey: String) {
    companion object {
        private var client = OkHttpClient()
    }

    private val endpoint = "https://api.themoviedb.org/3"
    private val urlBuilder = endpoint.toHttpUrl()
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val tmdbUpcomingResponseAdapter = moshi.adapter(TMDBUpcomingResponse::class.java)

    fun getUpcoming(page: Int): List<TMDBMovie> {
        val url = urlBuilder.newBuilder().addPathSegments("movie/upcoming")
            .addQueryParameter("api_key", apikey)
            .addQueryParameter("language", "ru-RU")
            .addQueryParameter("page", page.toString())
            .build()
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return listOf()
            val json =
                tmdbUpcomingResponseAdapter.fromJson(response.body!!.source()) ?: return listOf()
            return json.results
        }
    }
}