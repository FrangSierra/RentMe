package com.idealista.android.challenge.list.ui.list

import com.idealista.android.challenge.core.BasePresenter
import com.idealista.android.challenge.core.api.model.CommonError
import com.idealista.android.challenge.core.wrench.executor.UseCaseExecutor
import com.idealista.android.challenge.core.wrench.usecase.UseCase
import com.idealista.android.challenge.list.data.ListRepository
import com.idealista.android.challenge.list.data.persistence.PersistenceController
import com.idealista.android.challenge.list.domain.List
import com.idealista.android.challenge.list.domain.list
import com.idealista.android.challenge.list.ui.AdModel

class ListPresenter(private val listRepository: ListRepository,
                    private val persistenceController: PersistenceController,
                    private val executor : UseCaseExecutor) : BasePresenter<ListView>() {

    fun onListNeeded() {
        view.refreshing(true)
        UseCase<CommonError, List>()
            .bg(list(listRepository))
            .map { it.toModel(persistenceController.getFavoriteAdIds()) } //Match with favorites
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

    fun onAdClicked(ad: AdModel) {
        view.navigateToAdDetail(ad)
    }

    fun onAdMarkedAsFavorite(ad: AdModel) {
        val newFavoriteAds = persistenceController.markAdAsFavorite(ad.id)
        view.renderFavoriteAds(newFavoriteAds)
    }
}