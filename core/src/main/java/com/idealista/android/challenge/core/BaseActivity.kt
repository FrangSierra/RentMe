package com.idealista.android.challenge.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.annotations.TestOnly
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

abstract class BaseActivity<V : BaseView, T : BasePresenter<V>> : AppCompatActivity(), KodeinAware {
    abstract val presenter: T
    abstract val view : V

    protected val errorHandler : ErrorHandler by instance()

    override val kodein: Kodein by kodein()
    // Implement here common functionality

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!skipBinding) presenter.bindView(view)
    }

    private var skipBinding = false
    @TestOnly
    fun skipBinding(){
        skipBinding = true
    }
}