package com.android.kotlinmvp.views

import com.android.kotlinmvp.models.Movie

interface MoviesListView : BaseView {
    fun onMoviesListSuccess(moviesList: MutableList<Movie>)
}