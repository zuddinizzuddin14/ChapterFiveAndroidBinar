package com.example.mymovieapp.data.server.services

import com.example.mymovieapp.BuildConfig
import com.example.mymovieapp.data.server.models.MovieModel
import com.example.mymovieapp.data.server.response.MovieResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface TmdbApiService {

    @GET("movie/popular")
    suspend fun popularMovies(
        @Query("page", encoded = true) page: Int = PAGE_NUMBER,
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun topRatedMovies(
        @Query("page", encoded = true) page: Int = PAGE_NUMBER,
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun upComingMovies(
        @Query("page", encoded = true) page: Int = PAGE_NUMBER,
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun nowPlayingMovies(
        @Query("page", encoded = true) page: Int = PAGE_NUMBER,
    ): MovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun similarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page", encoded = true) page: Int = PAGE_NUMBER,
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query", encoded = true) query: String,
    ): MovieResponse

    companion object {

        private const val PAGE_NUMBER = 1

        @JvmStatic
        operator fun invoke(): TmdbApiService {
            val authInterceptor = Interceptor {
                val originRequest = it.request()
                val newUrl = originRequest.url.newBuilder().apply {
                    addQueryParameter("api_key", BuildConfig.API_KEY)
                }.build()
                it.proceed(originRequest.newBuilder().url(newUrl).build())
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(TmdbApiService::class.java)
        }
    }
}