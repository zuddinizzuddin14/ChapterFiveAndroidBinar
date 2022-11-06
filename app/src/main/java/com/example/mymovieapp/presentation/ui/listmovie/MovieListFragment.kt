package com.example.mymovieapp.presentation.ui.listmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.R
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.databinding.FragmentMovieListBinding
import com.example.mymovieapp.presentation.ui.ItemClick
import com.example.mymovieapp.presentation.ui.detailmovie.DetailMovieActivity
import com.example.mymovieapp.presentation.ui.adapter.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null

    private val binding by lazy { _binding!! }

    private val viewModel: MovieListViewModel by viewModels()

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(object : ItemClick {
            override fun onItemClicked(item: MovieModel) {
                startActivity(DetailMovieActivity.newInstance(requireContext(), item.id))
            }
        })
    }

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initList()
        navController = Navigation.findNavController(view)
    }

    private fun observeData() {
        viewModel.movieResult.observe(viewLifecycleOwner) {
            if (it.payload!!.isEmpty()) {
                adapter.clearMovieListItems()
            } else {
                val titleGroup = arrayListOf(
                    getString(R.string.text_trending),
                    getString(R.string.text_popular),
                    getString(R.string.text_now_playing),
                    getString(R.string.text_top_rated),
                    getString(R.string.text_up_coming)
                )
                adapter.setMovieListItems(titleGroup, it.payload)
            }
        }
    }

    private fun initList() {
        binding.rvMovieList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MovieListFragment.adapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.movieResult()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}