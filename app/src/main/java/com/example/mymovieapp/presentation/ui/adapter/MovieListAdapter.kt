package com.example.mymovieapp.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.databinding.ItemGroupMovieBinding
import com.example.mymovieapp.presentation.ui.ItemClick

class MovieListAdapter(private val itemClick: ItemClick) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    private var movieListItems: MutableList<MovieResponse> = mutableListOf()

    private var titleGroup: MutableList<String> = mutableListOf()

    fun setMovieListItems(titleGroup: List<String>,movieList: List<MovieResponse>) {
        this.titleGroup.clear()
        this.titleGroup.addAll(titleGroup)
        this.movieListItems.clear()
        this.movieListItems.addAll(movieList)
        notifyDataSetChanged()
    }

    fun clearMovieListItems() {
        this.movieListItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = ItemGroupMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bindView(titleGroup[position], movieListItems[position], position)
    }

    override fun getItemCount(): Int = movieListItems.size

    inner class MovieListViewHolder(private val binding: ItemGroupMovieBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        private val movieItemAdapter: MovieItemAdapter by lazy {
            MovieItemAdapter(object : ItemClick {
                override fun onItemClicked(item: MovieModel) {
                    itemClick.onItemClicked(item)
                }
            })
        }

        private val movieTrendingItemAdapter: MovieTrendingItemAdapter by lazy {
            MovieTrendingItemAdapter(object : ItemClick {
                override fun onItemClicked(item: MovieModel) {
                    itemClick.onItemClicked(item)
                }
            })
        }

        fun bindView(name: String, item: MovieResponse, position: Int) {
            binding.tvMovieGroup.text = name
            binding.rvMovieGroup.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            if (position >= 1) {
                binding.tvTitleApp.visibility = View.GONE
                binding.ivLogo.visibility = View.GONE
                binding.rvMovieGroup.adapter = movieItemAdapter
                movieItemAdapter.clearMovieItems()
                movieItemAdapter.setMovieItems(item)
            } else {
                binding.tvTitleApp.visibility = View.VISIBLE
                binding.ivLogo.visibility = View.VISIBLE
                binding.rvMovieGroup.adapter = movieTrendingItemAdapter
                movieTrendingItemAdapter.clearMovieTrendingItems()
                movieTrendingItemAdapter.setMovieTrendingItems(item)
            }
        }
    }
}