package com.example.mymovieapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mymovieapp.R
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.databinding.ItemTrendingBinding
import com.example.mymovieapp.presentation.ui.ItemClick

class MovieTrendingItemAdapter(private val itemClick: ItemClick) :
    RecyclerView.Adapter<MovieTrendingItemAdapter.MovieTrendingItemViewHolder>() {

    private var movieTrendingItems: MutableList<MovieModel> = mutableListOf()

    fun setMovieTrendingItems(movieTrendingItems: MovieResponse) {
        this.movieTrendingItems.clear()
        this.movieTrendingItems.addAll(movieTrendingItems.results)
        notifyDataSetChanged()
    }

    fun clearMovieTrendingItems() {
        this.movieTrendingItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTrendingItemViewHolder {
        val binding = ItemTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieTrendingItemViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MovieTrendingItemViewHolder, position: Int) {
        holder.bindView(movieTrendingItems[position], position)
    }

    override fun getItemCount(): Int = movieTrendingItems.size

    inner class MovieTrendingItemViewHolder(private val binding: ItemTrendingBinding, private val itemClick: ItemClick) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: MovieModel, position: Int) {
            with(item) {
                itemView.setOnClickListener { itemClick.onItemClicked(this) }
                if (position >= 1) binding.ivGap.visibility = View.GONE
                else binding.ivGap.visibility = View.INVISIBLE
                binding.tvTitleMovie.text = item.title
                if (item.backdropPath.isEmpty().not()) {
                    binding.ivBackdropMovie.load("https://image.tmdb.org/t/p/w500/" + item.backdropPath) {
                        crossfade(true)
                        placeholder(R.color.light_black)
                    }
                }
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