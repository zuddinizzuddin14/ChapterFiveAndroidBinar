package com.example.mymovieapp.presentation.ui.searchmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.R
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.databinding.ActivityMovieSearchBinding
import com.example.mymovieapp.presentation.ui.detailmovie.DetailMovieActivity
import com.example.mymovieapp.presentation.ui.ItemClick
import com.example.mymovieapp.presentation.ui.searchmovie.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchActivity : AppCompatActivity() {

    private val binding: ActivityMovieSearchBinding by lazy {
        ActivityMovieSearchBinding.inflate(layoutInflater)
    }
    private val viewModel: MovieSearchViewModel by viewModels()

    private val adapter: SearchAdapter by lazy {
        SearchAdapter(object : ItemClick {
            override fun onItemClicked(item: MovieModel) {
                startActivity(DetailMovieActivity.newInstance(this@MovieSearchActivity, item))
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initList()
        observeData()
    }

    private fun getSearch(query: String) {
        viewModel.searchMovie(query)
    }

    private fun observeData() {
        viewModel.loadingState.observe(this) { isLoading ->
            binding.pbPost.isVisible = isLoading
            binding.rvSearch.isVisible = !isLoading
        }
        viewModel.errorState.observe(this) { errorData ->
            binding.tvError.isVisible = errorData.first
            errorData.second?.message?.let {
                binding.tvError.text = it
            }
        }
        viewModel.searchResult.observe(this) {
            if (it.payload!!.results.isEmpty()) {
                adapter.clearItems()
                binding.tvError.isVisible = true
                binding.tvError.text = getString(R.string.text_empty)
            } else {
                adapter.setItems(it.payload.results)
            }
        }
    }

    private fun initList() {
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(this@MovieSearchActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@MovieSearchActivity.adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val search = menu.findItem(R.id.menu_item_search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    getSearch(query)
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

}