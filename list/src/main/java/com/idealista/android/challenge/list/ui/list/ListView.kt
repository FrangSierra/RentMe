package com.idealista.android.challenge.list.ui.list

import com.idealista.android.challenge.core.BaseView
import com.idealista.android.challenge.core.api.model.CommonError
import com.idealista.android.challenge.list.ui.AdModel

interface ListView : BaseView {
    fun refreshing(refreshing: Boolean)
    fun render(list: ListModel)
    fun renderError(error: CommonError)
    fun navigateToAdDetail(ad: AdModel)
    fun renderFavoriteAds(newFavoriteAds: Set<String>)
}