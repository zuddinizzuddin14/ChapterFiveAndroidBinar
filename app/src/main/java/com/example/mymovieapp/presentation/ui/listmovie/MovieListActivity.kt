package com.example.mymovieapp.presentation.ui.listmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mymovieapp.R
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.databinding.ActivityMovieListBinding
import com.example.mymovieapp.presentation.ui.ItemClick
import com.example.mymovieapp.presentation.ui.profileuser.ProfileActivity
import com.example.mymovieapp.presentation.ui.detailmovie.DetailMovieActivity
import com.example.mymovieapp.presentation.ui.listmovie.adapter.*
import com.example.mymovieapp.presentation.ui.loginuser.LoginActivity
import com.example.mymovieapp.presentation.ui.searchmovie.MovieSearchActivity
import com.example.mymovieapp.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    private val binding: ActivityMovieListBinding by lazy {
        ActivityMovieListBinding.inflate(layoutInflater)
    }

    private val viewModel: MovieListViewModel by viewModels()

    private val popularAdapter: PopularAdapter by lazy {
        PopularAdapter (object : ItemClick {
            override fun onItemClicked(item: MovieModel) {
                startActivity(DetailMovieActivity.newInstance(this@MovieListActivity, item))
            }
        })
    }
    private val nowPlayingAdapter: NowPlayingAdapter by lazy {
        NowPlayingAdapter(object : ItemClick {
            override fun onItemClicked(item: MovieModel) {
                startActivity(DetailMovieActivity.newInstance(this@MovieListActivity, item))
            }
        })
    }
    private val upComingAdapter: UpComingAdapter by lazy {
        UpComingAdapter (object : ItemClick {
            override fun onItemClicked(item: MovieModel) {
                startActivity(DetailMovieActivity.newInstance(this@MovieListActivity, item))
            }
        })
    }
    private val topRatedAdapter: TopRatedAdapter by lazy {
        TopRatedAdapter (object : ItemClick {
            override fun onItemClicked(item: MovieModel) {
                startActivity(DetailMovieActivity.newInstance(this@MovieListActivity, item))
            }
        })
    }

    private val convert = ImageUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initList()
        observeData()
        setClickListeners()
    }

    private fun observeData() {
        viewModel.loadingState.observe(this) { isLoading ->
            binding.pbPost.isVisible = isLoading
            binding.rvPopular.isVisible = !isLoading
            binding.rvNowPlaying.isVisible = !isLoading
        }
        viewModel.errorState.observe(this) { errorData ->
            binding.tvError.isVisible = errorData.first
            errorData.second?.message?.let {
                binding.tvError.text = it
            }
        }
        viewModel.popularResult.observe(this) {
            if (it.payload!!.results.isEmpty()) {
                popularAdapter.clearItems()
                binding.tvError.isVisible = true
                binding.tvError.text = getString(R.string.text_empty)
            } else {
                popularAdapter.setItems(it.payload.results)
            }
        }
        viewModel.nowPlayingResult.observe(this) {
            if (it.payload!!.results.isEmpty()) {
                nowPlayingAdapter.clearItems()
                binding.tvError.isVisible = true
                binding.tvError.text = getString(R.string.text_empty)
            } else {
                nowPlayingAdapter.setItems(it.payload.results)
            }
        }
        viewModel.upComingResult.observe(this) {
            if (it.payload!!.results.isEmpty()) {
                upComingAdapter.clearItems()
                binding.tvError.isVisible = true
                binding.tvError.text = getString(R.string.text_empty)
            } else {
                upComingAdapter.setItems(it.payload.results)
            }
        }
        viewModel.topRatedResult.observe(this) {
            if (it.payload!!.results.isEmpty()) {
                topRatedAdapter.clearItems()
                binding.tvError.isVisible = true
                binding.tvError.text = getString(R.string.text_empty)
            } else {
                topRatedAdapter.setItems(it.payload.results)
            }
        }
        viewModel.nameResult().observe(this) {
            binding.tvNameUser.text = it.toString()
        }
        viewModel.imageResult().observe(this) {
            if (!it.equals("null")) {
                binding.ivAccount.load(convert.stringToBitMap(it)) {
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    private fun initList() {
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(this@MovieListActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@MovieListActivity.popularAdapter
        }
        binding.rvNowPlaying.apply {
            layoutManager = LinearLayoutManager(this@MovieListActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@MovieListActivity.nowPlayingAdapter
        }
        binding.rvUpComing.apply {
            layoutManager = LinearLayoutManager(this@MovieListActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@MovieListActivity.upComingAdapter
        }
        binding.rvTopRated.apply {
            layoutManager = LinearLayoutManager(this@MovieListActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@MovieListActivity.topRatedAdapter
        }

    }

    private fun setClickListeners() {
        binding.ivAccount.setOnClickListener {
            startActivity(Intent(this@MovieListActivity, ProfileActivity::class.java))
        }
        binding.ivSearch.setOnClickListener {
            startActivity(Intent(this@MovieListActivity, MovieSearchActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
        checkUserlogin()
    }

    private fun getData() {
        viewModel.popularResult()
        viewModel.nowPlayingResult()
        viewModel.upComingResult()
        viewModel.topRatedResult()
    }

    private fun checkUserlogin() {
        binding.constraintMenu.isVisible = false
        viewModel.getSession().observe(this@MovieListActivity) {
            if (!it) {
                startActivity(Intent(this@MovieListActivity, LoginActivity::class.java))
            } else {
                binding.constraintMenu.isVisible = true
            }
        }
    }

}