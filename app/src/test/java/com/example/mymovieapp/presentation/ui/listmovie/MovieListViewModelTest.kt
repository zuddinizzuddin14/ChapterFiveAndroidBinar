package com.example.mymovieapp.presentation.ui.listmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mymovieapp.data.repositories.MovieRepository
import com.example.mymovieapp.data.server.response.MovieResponse
import com.example.mymovieapp.data.server.services.ApiHelper
import com.example.mymovieapp.data.server.services.ApiService
import com.example.mymovieapp.wrapper.Resource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieListViewModelTest {

    private lateinit var service: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var repository: MovieRepository
    private lateinit var viewModel: MovieListViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        service = mockk()
        apiHelper = ApiHelper(service)
        repository = MovieRepository(apiHelper)
        viewModel = MovieListViewModel(repository)
    }

    @Test
    fun getMovieResult(): Unit = runBlocking {
        val movieResult = mockk<List<MovieResponse>>()

        every {
            runBlocking {
                repository.getListMovie().payload
            }
        } returns movieResult

        viewModel.movieResult()
        viewModel.movieResult.value?.payload

        verify {
            runBlocking {
                repository.getListMovie().payload
            }
        }
    }
}