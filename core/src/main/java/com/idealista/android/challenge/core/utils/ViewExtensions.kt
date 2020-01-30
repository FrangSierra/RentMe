package com.idealista.android.challenge.core.utils

import android.view.View


/**
 * Makes the [View] visible.
 */
fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

/**
 * Makes the [View] gone.
 */
fun View.makeGone() {
    this.visibility = View.GONE
}