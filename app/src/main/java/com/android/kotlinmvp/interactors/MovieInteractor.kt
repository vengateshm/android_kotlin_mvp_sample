package com.android.kotlinmvp.interactors

import com.android.kotlinmvp.models.Movie
import com.android.kotlinmvp.network.ApiClient
import com.android.kotlinmvp.network.response.TopRatedMovies
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers

class MovieInteractor {
    // Get top rated movies
    // RxJava implementation
    fun getTopRatedMovies(apiKey: String, language: String, page: Int): Single<TopRatedMovies> {
        return ApiClient.getMoviesApi()
                .getTopRatedMovies(apiKey, language, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}