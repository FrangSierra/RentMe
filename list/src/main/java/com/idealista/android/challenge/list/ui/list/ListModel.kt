package com.idealista.android.challenge.list.ui.list

import com.idealista.android.challenge.list.ui.AdModel
import com.idealista.android.challenge.list.ui.toModel

data class ListModel(val ads: List<AdModel>)

fun com.idealista.android.challenge.list.domain.List.toModel(favoriteAds : Set<String>) =
    ListModel(ads.map {
        it.toModel(
            favoriteAds.contains(it.id)
        )
    })