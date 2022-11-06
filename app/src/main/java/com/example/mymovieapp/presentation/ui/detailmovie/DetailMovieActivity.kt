package com.example.mymovieapp.presentation.ui.detailmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.databinding.ActivityDetailMovieBinding
import com.example.mymovieapp.presentation.ui.ItemClick
import com.example.mymovieapp.presentation.ui.adapter.MovieItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private val movieId: Int by lazy {
        intent?.getIntExtra(MOVIE_ID, 0) as Int
    }

    private val binding: ActivityDetailMovieBinding by lazy {
        ActivityDetailMovieBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMovieViewModel by viewModels()

    private val adapter: MovieItemAdapter by lazy {
        MovieItemAdapter(object : ItemClick {
            override fun onItemClicked(item: MovieModel) {
                startActivity(newInstance(this@DetailMovieActivity, item.id))
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.cbFavorite.setOnCheckedChangeListener { checkBox, isChecked ->

        }
    }

    private fun observeData() {
        viewModel.detailMovieResult.observe(this@DetailMovieActivity) {
            viewModel.setItem(binding, it.payload!!)
        }
        viewModel.similarResult.observe(this@DetailMovieActivity) {
            if (it.payload!!.results.isEmpty()) {
                adapter.clearMovieItems()
            } else {
                adapter.setMovieItems(it.payload)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.detailMovieResult(movieId)
        viewModel.similarResult(movieId)
    }

    private fun initList() {
        binding.rvSimilar.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@DetailMovieActivity.adapter
        }
    }

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"

        @JvmStatic
        fun newInstance(context: Context, movieId: Int): Intent =
            Intent(context, DetailMovieActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
    }
}