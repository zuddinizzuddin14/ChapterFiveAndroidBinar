package com.example.mymovieapp.presentation.ui.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mymovieapp.data.repositories.MovieRepository
import com.example.mymovieapp.data.server.response.DetailMovieResponse
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.data.server.services.ApiHelper
import com.example.mymovieapp.data.server.services.ApiService
import com.example.mymovieapp.presentation.ui.listmovie.MovieListViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailMovieViewModelTest {
    private lateinit var service: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var repository: MovieRepository
    private lateinit var viewModel: DetailMovieViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        service = mockk()
        apiHelper = ApiHelper(service)
        repository = MovieRepository(apiHelper)
        viewModel = DetailMovieViewModel(repository)
    }

    @Test
    fun getDetailMovieResult(): Unit = runBlocking {
        val detailMovieResult = mockk<DetailMovieResponse>()

        every {
            runBlocking {
                repository.getDetailMovies(550).payload
            }
        } returns detailMovieResult

        viewModel.detailMovieResult(550)
        viewModel.detailMovieResult.value?.payload

        verify {
            runBlocking {
                repository.getDetailMovies(550).payload
            }
        }
    }

    @Test
    fun getSimilarResult(): Unit = runBlocking {
        val similarResult = mockk<MovieResponse>()

        every {
            runBlocking {
                repository.getSimilarMovies(550).payload
            }
        } returns similarResult

        viewModel.similarResult(550)
        viewModel.similarResult.value?.payload

        verify {
            runBlocking {
                repository.getSimilarMovies(550).payload
            }
        }
    }

}