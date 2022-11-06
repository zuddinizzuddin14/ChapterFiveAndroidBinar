package com.example.mymovieapp.presentation.ui.detailmovie

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.load
import com.example.mymovieapp.R
import com.example.mymovieapp.data.repositories.MovieRepository
import com.example.mymovieapp.data.server.response.DetailMovieResponse
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.databinding.ActivityDetailMovieBinding
import com.example.mymovieapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val repository: MovieRepository
    ): ViewModel() {

    val detailMovieResult = MutableLiveData<Resource<DetailMovieResponse>>()
    val similarResult = MutableLiveData<Resource<MovieResponse>>()
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()

    fun detailMovieResult(movieId: Int) {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            errorState.postValue(Pair(false, null))
            try {
                val data = repository.getDetailMovies(movieId)
                viewModelScope.launch(Dispatchers.Main) {
                    detailMovieResult.postValue(data)
                    loadingState.postValue(false)
                    errorState.postValue(Pair(false, null))
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    loadingState.postValue(false)
                    errorState.postValue(Pair(true, e))
                }
            }
        }
    }

    fun similarResult(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getSimilarMovies(movieId)
            viewModelScope.launch(Dispatchers.Main) {
                similarResult.postValue(data)
            }
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    fun setItem(binding: ActivityDetailMovieBinding, item: DetailMovieResponse) {
        binding.tvTitleMovie.text = item.title
        if (item.releaseDate.isNullOrEmpty().not()) {
            val parser = SimpleDateFormat("yyyy-MM-dd")
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            binding.tvDate.text = formatter.format(parser.parse(item.releaseDate) as Date)
        } else {
            binding.tvDate.text = "-"
        }
        if (item.genres.isNullOrEmpty().not()) {
            val sb = StringBuilder()
            for (i in item.genres.listIterator()) { sb.append(i.name + ", ") }
            binding.tvGenre.text = sb.deleteCharAt(sb.lastIndexOf(String())-2)
        } else {
            binding.tvGenre.text = "-"
        }
        binding.tvRatingMovie.text = "${item.voteAverage} (${item.voteCount})"
        binding.tvDescriptionMovie.text = item.overview
        if (item.backdropPath.isNullOrEmpty().not()) {
            binding.ivBackdropMovie.load("https://image.tmdb.org/t/p/w500/" + item.backdropPath) {
                crossfade(true)
                placeholder(R.color.light_black)
            }
        }
        if (item.posterPath.isNullOrEmpty().not()) {
            binding.ivPosterMovie.load("https://image.tmdb.org/t/p/w500/" + item.posterPath) {
                crossfade(true)
                placeholder(R.color.light_black)
            }
        }
    }
}