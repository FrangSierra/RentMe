package com.idealista.android.challenge.list

import com.idealista.android.challenge.list.data.ListDataSource
import com.idealista.android.challenge.list.data.ListRepository
import com.idealista.android.challenge.list.data.persistence.DiskPersistence
import com.idealista.android.challenge.list.data.persistence.PersistenceController
import com.idealista.android.challenge.list.data.persistence.SharedPreferencePersistence
import com.idealista.android.challenge.list.ui.detail.AdsPresenter
import com.idealista.android.challenge.list.ui.list.ListPresenter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object ListModule {
    private const val MODULE_NAME = "list"

    fun create(): Kodein.Module = Kodein.Module(MODULE_NAME) {
        bind<ListDataSource>() with singleton { ListDataSource(instance()) }
        bind<ListPresenter>() with singleton { ListPresenter(instance(), instance(), instance()) }
        bind<ListRepository>() with singleton { ListRepository(instance()) }
        bind<AdsPresenter>() with singleton { AdsPresenter(instance(), instance(), instance()) }
        bind<DiskPersistence>() with singleton { SharedPreferencePersistence(instance()) }
        bind<PersistenceController>() with singleton { PersistenceController(instance()) }
    }
}