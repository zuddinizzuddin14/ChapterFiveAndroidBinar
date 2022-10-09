package com.example.mymovieapp.presentation.ui.listmovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.local.database.entity.UserEntity
import com.example.mymovieapp.data.repositories.MovieRepository
import com.example.mymovieapp.data.repositories.UserRepository
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieRepository: MovieRepository,
    private val userRepository: UserRepository
    ): ViewModel() {

    val userResult = MutableLiveData<Resource<UserEntity?>>()
    val popularResult = MutableLiveData<Resource<MovieResponse>>()
    val topRatedResult = MutableLiveData<Resource<MovieResponse>>()
    val upComingResult = MutableLiveData<Resource<MovieResponse>>()
    val nowPlayingResult = MutableLiveData<Resource<MovieResponse>>()
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()

    fun getSession(): Boolean {
        return userRepository.getSession()
    }

    fun userResult() {
        val userId = userRepository.getUserId()
        viewModelScope.launch {
            userResult.postValue(userRepository.getUserById(userId))
        }
    }

    fun popularResult() {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            errorState.postValue(Pair(false, null))
            try {
                val data = movieRepository.getPopularMovies()
                viewModelScope.launch(Dispatchers.Main) {
                    popularResult.postValue(data)
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

    fun topRatedResult() {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            errorState.postValue(Pair(false, null))
            try {
                val data = movieRepository.getTopRatedMovies()
                viewModelScope.launch(Dispatchers.Main) {
                    topRatedResult.postValue(data)
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

    fun nowPlayingResult() {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            errorState.postValue(Pair(false, null))
            try {
                val data = movieRepository.getNowPlayingMovies()
                viewModelScope.launch(Dispatchers.Main) {
                    nowPlayingResult.postValue(data)
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

    fun upComingResult() {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            errorState.postValue(Pair(false, null))
            try {
                val data = movieRepository.getUpComingMovies()
                viewModelScope.launch(Dispatchers.Main) {
                    upComingResult.postValue(data)
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

}