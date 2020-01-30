package com.idealista.android.challenge.core

abstract class BasePresenter<T : BaseView> {
    protected lateinit var view : T

    fun bindView(view: T){
       this.view = view
    }
}