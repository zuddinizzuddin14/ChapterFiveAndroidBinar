package com.example.mymovieapp.data.server.services

import com.example.mymovieapp.data.server.response.DetailMovieResponse
import com.example.mymovieapp.data.server.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("trending/movie/{time_window}")
    suspend fun trendingMovies(
        @Path("time_window") timeWindow: String = "week",
    ): MovieResponse

    @GET("movie/popular")
    suspend fun popularMovies(
        @Query("page", encoded = true) page: Int = 1,
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun topRatedMovies(
        @Query("page", encoded = true) page: Int = 1,
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun upComingMovies(
        @Query("page", encoded = true) page: Int = 1,
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun nowPlayingMovies(
        @Query("page", encoded = true) page: Int = 1,
    ): MovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun similarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page", encoded = true) page: Int = 1,
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query", encoded = true) query: String,
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun detailMovie(
        @Path("movie_id") movieId: Int,
    ): DetailMovieResponse

}