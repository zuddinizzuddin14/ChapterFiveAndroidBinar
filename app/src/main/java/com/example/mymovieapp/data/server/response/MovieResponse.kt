package com.example.mymovieapp.data.server.response

import com.example.mymovieapp.data.server.models.MovieModel
import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieModel>

)
