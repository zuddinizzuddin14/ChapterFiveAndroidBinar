package com.example.mymovieapp.data.server.services

import com.example.mymovieapp.data.server.response.DetailMovieResponse
import com.example.mymovieapp.data.server.response.MovieResponse

class ApiHelper(private val apiService: ApiService) {

    suspend fun trendingMovies(): MovieResponse = apiService.trendingMovies()

    suspend fun popularMovies(): MovieResponse = apiService.popularMovies()

    suspend fun topRatedMovies(): MovieResponse = apiService.topRatedMovies()

    suspend fun upComingMovies(): MovieResponse = apiService.upComingMovies()

    suspend fun nowPlayingMovies(): MovieResponse = apiService.nowPlayingMovies()

    suspend fun similarMovies(movieId: Int): MovieResponse = apiService.similarMovies(movieId)

    suspend fun searchMovie(query: String): MovieResponse = apiService.searchMovie(query)

    suspend fun detailMovie(movieId: Int): DetailMovieResponse = apiService.detailMovie(movieId)

}