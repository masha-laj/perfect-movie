package com.example.perfect_movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.perfect_movie.redux.tmdbClient
import com.example.perfect_movie.tmdb_api.TMDBMovie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PARAM_MOVIE = "detail:movie"
        const val IMAGE_ANIMATION_NAME = "detail:image_anim"
        const val TITLE_ANIMATION_NAME = "detail:title_anim"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movie = intent.getParcelableExtra<TMDBMovie>(EXTRA_PARAM_MOVIE) ?: return
        if (!movie.backdrop_path.isNullOrBlank())
            Picasso.get().load(tmdbClient.getImageUrl(movie.backdrop_path!!))
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error).into(d_backdropIV)
        d_titleTV.text = movie.title
        d_releaseTV.text = "Дата релиза: ${movie.release_date}"
        d_ratingRB.rating = movie.vote_average / 2
        d_ratingTV.text = (movie.vote_average / 2).toString()
        d_descriptionTV.text = movie.overview

        ViewCompat.setTransitionName(d_backdropIV, IMAGE_ANIMATION_NAME);
        ViewCompat.setTransitionName(d_titleTV, TITLE_ANIMATION_NAME);
    }
}