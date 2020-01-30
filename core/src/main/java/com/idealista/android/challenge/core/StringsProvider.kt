package com.idealista.android.challenge.core

interface StringsProvider {

    fun string(id: Int, vararg args: Any?): String
    fun quantityString(id: Int, quantity: Int, vararg args: Any?): String

}