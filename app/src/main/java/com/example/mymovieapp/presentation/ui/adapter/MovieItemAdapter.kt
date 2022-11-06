package com.example.mymovieapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mymovieapp.R
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.databinding.ItemMovieBinding
import com.example.mymovieapp.presentation.ui.ItemClick

class MovieItemAdapter(private val itemClick: ItemClick) :
    RecyclerView.Adapter<MovieItemAdapter.MovieItemViewHolder>() {

    private var movieItems: MutableList<MovieModel> = mutableListOf()

    fun setMovieItems(movieItems: MovieResponse) {
        this.movieItems.clear()
        this.movieItems.addAll(movieItems.results)
        notifyDataSetChanged()
    }

    fun clearMovieItems() {
        this.movieItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieItemViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bindView(movieItems[position], position)
    }

    override fun getItemCount(): Int = movieItems.size

    inner class MovieItemViewHolder(private val binding: ItemMovieBinding, private val itemClick: ItemClick) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: MovieModel, position: Int) {
            with(item) {
                itemView.setOnClickListener { itemClick.onItemClicked(this) }
                if (position >= 1) binding.ivGap.visibility = View.GONE
                else binding.ivGap.visibility = View.INVISIBLE
                if (item.posterPath.isEmpty().not()) {
                    binding.ivPosterMovie.load("https://image.tmdb.org/t/p/w500/" + item.posterPath) {
                        crossfade(true)
                        placeholder(R.color.light_black)
                    }
                }
            }
        }
    }
}