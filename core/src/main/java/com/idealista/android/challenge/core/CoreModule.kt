package com.idealista.android.challenge.core

import com.idealista.android.challenge.core.api.ApiClient
import com.idealista.android.challenge.core.api.ListApi
import com.idealista.android.challenge.core.wrench.executor.ChallengeUseCaseExecutor
import com.idealista.android.challenge.core.wrench.executor.UseCaseExecutor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object CoreModule {
    private const val MODULE_NAME = "core"

    //This should be provided, but it's used across the models to avoid be passing the context
    lateinit var stringsProvider: StringsProvider

    fun create(): Kodein.Module = Kodein.Module(MODULE_NAME) {
        bind<ErrorHandler>() with singleton { DefaultErrorHandler(instance()) }
        bind<UseCaseExecutor>() with singleton { ChallengeUseCaseExecutor() }
        bind<ApiClient>() with singleton {
            ApiClient.Builder()
                .withEndPoint("https://api.myjson.com/")
                .create()
        }
        bind<ListApi>() with singleton { ListApi(instance()) }
    }
}