package com.example.perfect_movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.perfect_movie.redux.tmdbClient
import com.example.perfect_movie.tmdb_api.TMDBMovie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class MoviesAdapter : ListAdapter<TMDBMovie, MovieViewHolder>(DiffCallback()) {
    lateinit var clickListener: (View, TMDBMovie) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(movie: TMDBMovie, listener: (View, TMDBMovie) -> Unit) {
        if (!movie.backdrop_path.isNullOrBlank())
            Picasso.get().load(tmdbClient.getImageUrl(movie.backdrop_path!!))
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error).into(itemView.backdropIV)
        itemView.titleTV.text = movie.title
        itemView.releaseTV.text = movie.release_date
        itemView.ratingRB.rating = movie.vote_average / 2
        itemView.setOnClickListener { listener(itemView, movie) }
    }
}

class DiffCallback : DiffUtil.ItemCallback<TMDBMovie>() {
    override fun areItemsTheSame(oldItem: TMDBMovie, newItem: TMDBMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TMDBMovie, newItem: TMDBMovie): Boolean {
        return oldItem == newItem
    }
}