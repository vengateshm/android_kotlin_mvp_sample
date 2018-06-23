package com.android.kotlinmvp.network.api

import com.android.kotlinmvp.network.response.TopRatedMovies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String,
                          @Query("language") language: String,
                          @Query("page") page: Int): Single<TopRatedMovies>
}