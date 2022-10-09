package com.example.mymovieapp.data.repositories

import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.data.server.services.TmdbApiService
import com.example.mymovieapp.wrapper.Resource

interface MovieRepository {

    suspend fun searchMovie(query: String): Resource<MovieResponse>
    suspend fun getPopularMovies(): Resource<MovieResponse>
    suspend fun getTopRatedMovies(): Resource<MovieResponse>
    suspend fun getUpComingMovies(): Resource<MovieResponse>
    suspend fun getNowPlayingMovies(): Resource<MovieResponse>
    suspend fun getSimilarMovies(movieId: Int): Resource<MovieResponse>

}

class MovieRepositoryImpl(
    private val apiService : TmdbApiService
) : MovieRepository {
    override suspend fun searchMovie(query: String): Resource<MovieResponse> {
        return proceed {
            apiService.searchMovie(query)
        }
    }

    override suspend fun getPopularMovies(): Resource<MovieResponse> {
        return proceed {
            apiService.popularMovies()
        }
    }

    override suspend fun getTopRatedMovies(): Resource<MovieResponse> {
        return proceed {
            apiService.topRatedMovies()
        }
    }

    override suspend fun getUpComingMovies(): Resource<MovieResponse> {
        return proceed {
            apiService.upComingMovies()
        }
    }

    override suspend fun getNowPlayingMovies(): Resource<MovieResponse> {
        return proceed {
            apiService.nowPlayingMovies()
        }
    }

    override suspend fun getSimilarMovies(movieId: Int): Resource<MovieResponse> {
        return proceed {
            apiService.similarMovies(movieId)
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