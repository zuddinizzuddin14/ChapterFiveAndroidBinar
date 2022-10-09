package com.example.mymovieapp.presentation.ui

import com.example.mymovieapp.data.server.models.MovieModel

interface ItemClick {
    fun onItemClicked(item: MovieModel)
}