package com.example.mymovieapp.presentation.ui.detailmovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.load
import com.example.mymovieapp.R
import com.example.mymovieapp.data.repositories.MovieRepository
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.databinding.ActivityDetailMovieBinding
import com.example.mymovieapp.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val repository: MovieRepository): ViewModel() {

    val similarResult = MutableLiveData<Resource<MovieResponse>>()

    fun similarResult(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getSimilarMovies(movieId)
            viewModelScope.launch(Dispatchers.Main) {
                similarResult.postValue(data)
            }
        }
    }

    fun setItem(binding: ActivityDetailMovieBinding, item: MovieModel) {
        binding.tvTitleMovie.text = item.title
        binding.tvTitleMovie.text = item.title
        binding.tvRatingMovie.text = "${item.voteAverage} (${item.voteCount})"
        binding.tvDescriptionMovie.text = item.overview
        binding.ivBackdropMovie.load("https://image.tmdb.org/t/p/w500/" + item.backdropPath) {
            crossfade(true)
            placeholder(R.color.light_black)
        }
        binding.ivPosterMovie.load("https://image.tmdb.org/t/p/w500/" + item.posterPath) {
            crossfade(true)
            placeholder(R.color.light_black)
        }


    }

}