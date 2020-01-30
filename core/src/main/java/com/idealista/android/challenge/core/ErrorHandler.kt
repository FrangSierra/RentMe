package com.idealista.android.challenge.core

import android.content.Context
import android.widget.Toast
import com.idealista.android.challenge.core.api.model.CommonError

interface ErrorHandler {
    /** Generate an user friendly message for known errors. */
    fun showErrorMessage(e: CommonError?)

    /** Handle the error, may result in a new activity being launched. */
    fun handle(e: CommonError?): Boolean
}

class DefaultErrorHandler(private val context: Context) : ErrorHandler {

    override fun showErrorMessage(e: CommonError?) {
        if (e == null) return
        val message = when (e) {
            CommonError.NoNetwork -> R.string.no_networks_error
            CommonError.ServerError -> R.string.server_error
            CommonError.ClientError -> R.string.client_error
            CommonError.Forbidden -> R.string.forbidden_error
            CommonError.UnknownError -> R.string.default_error
            CommonError.NotFound -> R.string.not_found_error
            CommonError.Canceled -> R.string.canceled_error
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun handle(e: CommonError?): Boolean {
        //TODO handle specific errors that requires an special case(logout, clean shared prefs...)
        return false
    }
}