package com.example.mymovieapp.data.repositories

import com.example.mymovieapp.data.server.response.DetailMovieResponse
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.data.server.services.ApiHelper
import com.example.mymovieapp.wrapper.Resource

class MovieRepository(
    private val apiHelper: ApiHelper
) {
    suspend fun getListMovie(): Resource<List<MovieResponse>> {
        return proceed {
            buildList(5) {
                add(apiHelper.trendingMovies())
                add(apiHelper.popularMovies())
                add(apiHelper.nowPlayingMovies())
                add(apiHelper.topRatedMovies())
                add(apiHelper.upComingMovies())
            }
        }
    }

    suspend fun getDetailMovies(movieId: Int): Resource<DetailMovieResponse> {
        return proceed {
            apiHelper.detailMovie(movieId)
        }
    }

    suspend fun searchMovie(query: String): Resource<MovieResponse> {
        return proceed {
            apiHelper.searchMovie(query)
        }
    }

    suspend fun getSimilarMovies(movieId: Int): Resource<MovieResponse> {
        return proceed {
            apiHelper.similarMovies(movieId)
        }
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}