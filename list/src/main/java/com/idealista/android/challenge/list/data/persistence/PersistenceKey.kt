package com.idealista.android.challenge.list.data.persistence

sealed class PersistenceKey<InputType> {
    object FavoriteAds : PersistenceKey<Set<String>>()

    fun toStringKey() = this::class.simpleName!!
}