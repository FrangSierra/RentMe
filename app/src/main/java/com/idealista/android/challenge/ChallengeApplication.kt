package com.idealista.android.challenge

import android.app.Application
import android.content.Context
import com.idealista.android.challenge.core.Addressable
import com.idealista.android.challenge.core.CoreModule
import com.idealista.android.challenge.list.ListModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class ChallengeApplication : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        Addressable.PACKAGE_NAME = packageName
        CoreModule.stringsProvider = StringsProvider(baseContext)
    }

    override val kodein = Kodein.lazy {
        bind<Context>() with instance(this@ChallengeApplication)
        import(CoreModule.create())
        import(ListModule.create())
    }
}