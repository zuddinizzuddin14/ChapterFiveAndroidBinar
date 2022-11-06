package com.example.mymovieapp.data.repositories

import com.example.mymovieapp.data.server.response.DetailMovieResponse
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.data.server.services.ApiHelper
import com.example.mymovieapp.data.server.services.ApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MovieRepositoryTest {

    private lateinit var service: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var repository: MovieRepository

    @Before
    fun setup() {
        service = mockk()
        apiHelper = ApiHelper(service)
        repository = MovieRepository(apiHelper)
    }

    @Test
    fun getlistMovie(): Unit = runBlocking {
        val listMovie = mockk<List<MovieResponse>>()

        every {
            runBlocking {
                buildList(5) {
                    add(apiHelper.trendingMovies())
                    add(apiHelper.popularMovies())
                    add(apiHelper.nowPlayingMovies())
                    add(apiHelper.topRatedMovies())
                    add(apiHelper.upComingMovies())
                }
            }
        } returns listMovie

        repository.getListMovie()

        verify {
            runBlocking {
                buildList(5) {
                    add(apiHelper.trendingMovies())
                    add(apiHelper.popularMovies())
                    add(apiHelper.nowPlayingMovies())
                    add(apiHelper.topRatedMovies())
                    add(apiHelper.upComingMovies())
                }
            }
        }
    }

    @Test
    fun getDetailMovies(): Unit = runBlocking {
        val detailMovie = mockk<DetailMovieResponse>()

        every {
            runBlocking {
                apiHelper.detailMovie(550)
            }
        } returns detailMovie

        repository.getDetailMovies(550)

        verify {
            runBlocking {
                apiHelper.detailMovie(550)
            }
        }
    }

    @Test
    fun searchMovie(): Unit = runBlocking {
        val searchMovie = mockk<MovieResponse>()

        every {
            runBlocking {
                apiHelper.searchMovie("avengers")
            }
        } returns searchMovie

        repository.searchMovie("avengers")

        verify {
            runBlocking {
                apiHelper.searchMovie("avengers")
            }
        }
    }

    @Test
    fun getSimilarMovies(): Unit = runBlocking {
        val similarMovie = mockk<MovieResponse>()

        every {
            runBlocking {
                apiHelper.similarMovies(550)
            }
        } returns similarMovie

        repository.getSimilarMovies(550)

        verify {
            runBlocking {
                apiHelper.similarMovies(550)
            }
        }
    }
}