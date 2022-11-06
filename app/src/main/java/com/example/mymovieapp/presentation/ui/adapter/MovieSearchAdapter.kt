package com.example.mymovieapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mymovieapp.R
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.databinding.ItemSearchBinding
import com.example.mymovieapp.presentation.ui.ItemClick
import java.text.SimpleDateFormat
import java.util.*

class MovieSearchAdapter(
    private val itemClick: ItemClick
) : RecyclerView.Adapter<MovieSearchAdapter.ViewHolder>() {

    private var items: MutableList<MovieModel> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<MovieModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ItemSearchBinding, val itemClick: ItemClick) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bindView(item: MovieModel) {
            with(item) {
                binding.root.setOnClickListener {
                    itemClick.onItemClicked(this)
                }
                binding.tvTitleMovie.text = item.title
                binding.tvRatingMovie.text = "${item.voteAverage} (${item.voteCount})"
                if (item.releaseDate.isEmpty()) {
                    binding.tvDate.text = "-"
                } else {
                    val parser = SimpleDateFormat("yyyy-MM-dd")
                    val formatter = SimpleDateFormat("dd/MM/yyyy")
                    binding.tvDate.text = formatter.format(parser.parse(item.releaseDate) as Date)
                }
                binding.tvDescriptionMovie.text = item.overview
                binding.ivPosterMovie.load("https://image.tmdb.org/t/p/w500/" + item.posterPath) {
                    crossfade(true)
                    placeholder(R.color.light_black)
                }
            }
        }
    }
}