package com.android.kotlinmvp.ui.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.kotlinmvp.R
import com.android.kotlinmvp.models.Movie
import com.android.kotlinmvp.presenters.MoviesListPresenter
import com.android.kotlinmvp.ui.adapters.MovieListAdapter
import com.android.kotlinmvp.utils.recyclerView.PaginationScrollListener
import com.android.kotlinmvp.views.MoviesListView

class MovieListActivity : AppCompatActivity(), MoviesListView {

    // lateinit can be applied only for var not val(since val is immutable)
    private lateinit var rvList: RecyclerView
    private lateinit var tvNoRecords: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var movieListAdapter: MovieListAdapter
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage: Int = 1
    private val totalPages: Int = 3

    private var presenter: MoviesListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Create presenter instance
        presenter = MoviesListPresenter()
        presenter!!.attachView(this)

        findViews()
        setRecyclerView()

        showProgress()
        getMovies(currentPage)
    }

    // Find views by ids
    private fun findViews() {
        rvList = findViewById(R.id.rvList)
        tvNoRecords = findViewById(R.id.tvNoRecords)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setRecyclerView() {
        val llm = LinearLayoutManager(this)
        rvList.layoutManager = llm

        rvList.addOnScrollListener(object : PaginationScrollListener(llm) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1

                getMovies(currentPage)
            }
        })

        rvList.setHasFixedSize(true)

        movieListAdapter = MovieListAdapter()
        rvList.adapter = movieListAdapter
    }

    private fun getMovies(currentPage: Int) {
        presenter?.getTopRatedMovies(getString(R.string.moviedb_api_key),
                "en_US", currentPage)
    }

    override fun getContext(): Context {
        return this
    }

    override fun onMoviesListSuccess(moviesList: MutableList<Movie>) {
        hideProgress()
        setMovies(moviesList)
    }

    private fun setMovies(moviesList: MutableList<Movie>) {
        if (currentPage == 1) {
            movieListAdapter.setMovies(moviesList)
            movieListAdapter.addLoadingFooter()
            movieListAdapter.notifyDataSetChanged()
            if (currentPage == totalPages) {
                movieListAdapter.removeLoadingFooter()
                isLastPage = true
            }
        } else {
            if (!moviesList.isEmpty()) {
                movieListAdapter.removeLoadingFooter()
                isLoading = false
                movieListAdapter.addAll(moviesList)
                movieListAdapter.addLoadingFooter()
            } else {
                movieListAdapter.removeLoadingFooter()
                isLoading = false
                isLastPage = true
            }
        }
    }

    override fun onError(message: String) {
        hideProgress()
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        // Remove view from presenter
        presenter?.detachView()
        presenter = null

        super.onDestroy()
    }
}
