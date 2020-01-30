package com.idealista.android.challenge.list.ui.detail

import com.idealista.android.challenge.core.BaseView
import com.idealista.android.challenge.core.api.model.CommonError
import com.idealista.android.challenge.list.ui.AdModel

interface AdsView : BaseView {
    fun render(ad: AdModel)
    fun renderError(error: CommonError)
    fun markAsFavorite(mark: Boolean)
    fun refreshing(refreshing: Boolean)
}