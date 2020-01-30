package com.idealista.android.challenge.list.ui.detail

import com.idealista.android.challenge.core.BasePresenter
import com.idealista.android.challenge.core.api.model.CommonError
import com.idealista.android.challenge.core.wrench.executor.UseCaseExecutor
import com.idealista.android.challenge.core.wrench.usecase.UseCase
import com.idealista.android.challenge.list.data.ListRepository
import com.idealista.android.challenge.list.data.persistence.PersistenceController
import com.idealista.android.challenge.list.domain.Ad
import com.idealista.android.challenge.list.domain.List
import com.idealista.android.challenge.list.domain.ad
import com.idealista.android.challenge.list.domain.list
import com.idealista.android.challenge.list.ui.AdModel
import com.idealista.android.challenge.list.ui.list.toModel
import com.idealista.android.challenge.list.ui.toModel


class AdsPresenter(private val listRepository: ListRepository,
                   private val executor : UseCaseExecutor,
                   private val persistenceController: PersistenceController) :
    BasePresenter<AdsView>() {

    fun markAsFavorite(ad: AdModel) {
        val newFavoriteAds = persistenceController.markAdAsFavorite(ad.id)
        view.markAsFavorite(newFavoriteAds.contains(ad.id))
    }

    fun onAdNeeded(id: String) {
        view.refreshing(true)
        UseCase<CommonError, Ad>()
            .bg(ad(id, listRepository))
            .map { it.toModel(persistenceController.getFavoriteAdIds().contains(id)) } //Match with favorites
            .ui {
                it.fold(
                    { error ->
                        view.renderError(error)
                    },
                    { list ->
                        view.render(list)
                    }
                )
                view.refreshing(false)
            }.run(executor)
    }
}