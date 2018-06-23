package com.android.kotlinmvp.views

import android.content.Context

interface BaseView {
    fun getContext(): Context
    fun onError(message: String)
}