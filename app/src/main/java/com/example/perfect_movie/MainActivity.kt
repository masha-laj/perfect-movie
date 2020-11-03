package com.example.perfect_movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.perfect_movie.tmdb_api.TMDBApi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread {
            val tmdbClient = TMDBApi(BuildConfig.TMDB_API_KEY)
            tmdbClient.getUpcoming(1)

            runOnUiThread {
                //Update UI
            }
        }.start()
    }
}