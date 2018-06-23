package com.android.kotlinmvp.presenters

interface BasePresenter<in View> {
    fun attachView(view: View)
    fun detachView()
}