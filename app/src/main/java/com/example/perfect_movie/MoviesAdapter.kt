package com.example.perfect_movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.perfect_movie.tmdb_api.TMDBMovie
import kotlinx.android.synthetic.main.item_list.view.*

class MoviesAdapter : ListAdapter<TMDBMovie, MovieViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(movie: TMDBMovie) {//, listener: (TMDBMovie) -> Unit) {
        itemView.titleTV.text = movie.title;
        //itemView.setOnClickListener { listener(movie) }
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