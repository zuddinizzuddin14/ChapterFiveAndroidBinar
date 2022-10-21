package com.example.mymovieapp.data.repositories

import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.data.server.services.ApiHelper
import com.example.mymovieapp.wrapper.Resource

class MovieRepository(
    private val apiHelper: ApiHelper
) {
   suspend fun searchMovie(query: String): Resource<MovieResponse> {
        return proceed {
            apiHelper.searchMovie(query)
        }
    }

   suspend fun getPopularMovies(): Resource<MovieResponse> {
        return proceed {
            apiHelper.popularMovies()
        }
    }

   suspend fun getTopRatedMovies(): Resource<MovieResponse> {
        return proceed {
            apiHelper.topRatedMovies()
        }
    }

   suspend fun getUpComingMovies(): Resource<MovieResponse> {
        return proceed {
            apiHelper.upComingMovies()
        }
    }

   suspend fun getNowPlayingMovies(): Resource<MovieResponse> {
        return proceed {
            apiHelper.nowPlayingMovies()
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