package com.android.kotlinmvp.network.response

import com.android.kotlinmvp.models.Movie
import com.google.gson.annotations.SerializedName

data class TopRatedMovies(
        @SerializedName("page") val page: Int,
        @SerializedName("results") val moviesList: ArrayList<Movie>,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int
)