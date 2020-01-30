package com.idealista.android.challenge.list.ui.list

import com.idealista.android.challenge.core.CoreModule
import com.idealista.android.challenge.core.StringsProvider
import com.idealista.android.challenge.core.api.model.CommonError
import com.idealista.android.challenge.core.wrench.executor.UseCaseExecutor
import com.idealista.android.challenge.core.wrench.type.Either
import com.idealista.android.challenge.list.TestData
import com.idealista.android.challenge.list.data.ListRepository
import com.idealista.android.challenge.list.data.persistence.PersistenceController
import com.idealista.android.challenge.list.domain.List
import com.idealista.android.challenge.list.ui.AdModel
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import strikt.api.expectThat
import strikt.assertions.isTrue

class ListPresenterTest {

    private val executor = object : UseCaseExecutor {
        override fun <Error, Result> execute(
            background: () -> Either<Error, Result>,
            ui: (Either<Error, Result>) -> Unit
        ) {
            ui.invoke(background.invoke())
        }
    }

    @Before
    fun setUp() {
        CoreModule.stringsProvider = mock(StringsProvider::class.java).apply {
            `when`(this.string(ArgumentMatchers.anyInt(), ArgumentMatchers.any()))
                .thenReturn("")
            `when`(
                this.quantityString(
                    ArgumentMatchers.anyInt(),
                    ArgumentMatchers.anyInt(),
                    ArgumentMatchers.any()
                )
            ).thenReturn("")
        }
    }

    @Test
    @Throws(Exception::class)
    fun `view render is correctly call when presenter bring back ads`() {
        val mockRepository = mock(ListRepository::class.java).apply {
            `when`(this.list())
                .thenReturn(Either.Right(List(TestData.sampleAdList)))
        }
        val mockController = mock(PersistenceController::class.java).apply {
            `when`(this.getFavoriteAdIds())
                .thenReturn(setOf(*TestData.sampleAdList.map { it.id }.toTypedArray()))
        }

        var rendered = false
        val view = prepareFakeView(onRender = { rendered = true })
        val presenter = ListPresenter(mockRepository, mockController, executor)

        presenter.bindView(view)
        presenter.onListNeeded()
        expectThat(rendered) { isTrue() }
    }

    private fun prepareFakeView(
        onRefreshed: (Boolean) -> Unit = {},
        onRender: (ListModel) -> Unit = {},
        onRenderError: (CommonError) -> Unit = {},
        onNavigateAd: (AdModel) -> Unit = {},
        onRenderFavoriteAds: (Set<String>) -> Unit = {}
    ) = object : ListView {
        override fun refreshing(refreshing: Boolean) = onRefreshed(refreshing)
        override fun render(list: ListModel) = onRender(list)
        override fun renderError(error: CommonError) = onRenderError(error)
        override fun navigateToAdDetail(ad: AdModel) = onNavigateAd(ad)
        override fun renderFavoriteAds(newFavoriteAds: Set<String>) =
            onRenderFavoriteAds(newFavoriteAds)
    }
}