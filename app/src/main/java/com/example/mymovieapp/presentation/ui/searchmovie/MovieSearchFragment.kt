package com.example.mymovieapp.presentation.ui.searchmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.R
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.databinding.FragmentMovieSearchBinding
import com.example.mymovieapp.presentation.ui.ItemClick
import com.example.mymovieapp.presentation.ui.detailmovie.DetailMovieActivity
import com.example.mymovieapp.presentation.ui.adapter.MovieSearchAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchFragment : Fragment() {

    private var _binding: FragmentMovieSearchBinding? = null

    private val binding by lazy { _binding!! }

    private val viewModel: MovieSearchViewModel by viewModels()

    private val adapter: MovieSearchAdapter by lazy {
        MovieSearchAdapter(object : ItemClick {
            override fun onItemClicked(item: MovieModel) {
                startActivity(DetailMovieActivity.newInstance(requireContext(), item.id))
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.svSearchImage.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchMovie(it)
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }



    private fun observeData() {
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.pbSearch.isVisible = isLoading
            binding.rvSearch.isVisible = !isLoading
        }
        viewModel.errorState.observe(viewLifecycleOwner) { errorData ->
            binding.tvError.isVisible = errorData.first
            errorData.second?.message?.let {
                binding.tvError.text = it
            }
        }
        viewModel.searchResult.observe(viewLifecycleOwner) {
            if (it.payload!!.results.isEmpty()) {
                adapter.clearItems()
                binding.tvError.isVisible = true
                binding.tvError.text = getString(R.string.text_empty_state)
            } else {
                adapter.setItems(it.payload.results)
            }
        }
    }

    private fun initList() {
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MovieSearchFragment.adapter
        }
    }

}