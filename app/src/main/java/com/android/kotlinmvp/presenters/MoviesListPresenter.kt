package com.android.kotlinmvp.presenters

import com.android.kotlinmvp.interactors.MovieInteractor
import com.android.kotlinmvp.views.MoviesListView
import io.reactivex.disposables.Disposable

class MoviesListPresenter : BasePresenter<MoviesListView> {

    private var view: MoviesListView? = null
    private var topRatedMoviesDisposable: Disposable? = null
    private var interactor: MovieInteractor? = null

    override fun attachView(view: MoviesListView) {
        this.view = view
        this.interactor = MovieInteractor()
    }

    fun getTopRatedMovies(apiKey: String, language: String, page: Int) {
        topRatedMoviesDisposable = interactor?.getTopRatedMovies(apiKey, language, page)
                ?.subscribe(
                        { topRatedMovies ->
                            view?.onMoviesListSuccess(topRatedMovies.moviesList)
                        },
                        { throwable ->
                            view?.onError(throwable.localizedMessage)
                        })
    }

    override fun detachView() {
        if (!topRatedMoviesDisposable?.isDisposed!!) {
            topRatedMoviesDisposable?.dispose()
        }
        this.view = null
        this.interactor = null
    }
}