package com.rosorio.mindicador.view.commons.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.rosorio.mindicador.R

class LoadingView constructor(
    context: Context,
    attr: AttributeSet
): RelativeLayout(context, attr) {
    init {
        inflate(context, R.layout.layout_loading, this)
    }

    fun showLoading() {
        visibility = View.VISIBLE
    }

    fun hideLoading() {
        visibility = View.GONE
    }
}