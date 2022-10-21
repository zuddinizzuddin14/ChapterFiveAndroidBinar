package com.example.mymovieapp.presentation.ui.detailmovie

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.databinding.ActivityDetailMovieBinding
import com.example.mymovieapp.presentation.ui.ItemClick
import com.example.mymovieapp.presentation.ui.detailmovie.adapter.SimilarAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private val movie: MovieModel by lazy {
        intent?.getSerializableExtra(EXTRA_MOVIE) as MovieModel
    }

    private val binding: ActivityDetailMovieBinding by lazy {
        ActivityDetailMovieBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMovieViewModel by viewModels()

    private val similarAdapter: SimilarAdapter by lazy {
        SimilarAdapter(object : ItemClick {
            override fun onItemClicked(item: MovieModel) {
                startActivity(newInstance(this@DetailMovieActivity, item))
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initList()
        setData()
        observeData()
    }

    private fun observeData() {
        viewModel.similarResult.observe(this) {
            if (it.payload!!.results.isEmpty()) {
                similarAdapter.clearItems()
            } else {
                similarAdapter.setItems(it.payload.results)
            }
        }
    }

    private fun setData() {
        viewModel.setItem(binding, movie)
        viewModel.similarResult(movie.id)
    }

    private fun initList() {
        binding.rvSimilar.apply {
            layoutManager = LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@DetailMovieActivity.similarAdapter
        }
    }

    companion object {
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"

        @JvmStatic
        fun newInstance(context: Context, movie: MovieModel): Intent =
            Intent(context, DetailMovieActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, movie)
            }
    }
}