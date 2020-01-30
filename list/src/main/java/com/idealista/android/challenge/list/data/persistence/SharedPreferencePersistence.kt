package com.idealista.android.challenge.list.data.persistence

import android.content.Context
import android.content.SharedPreferences

interface DiskPersistence {
    fun <T> insert(key: PersistenceKey<T>, value: T)

    fun <T> getValue(key: PersistenceKey<T>, default: T): T

    fun clear(key: PersistenceKey<*>)

    fun clearAll()
}


class SharedPreferencePersistence(context: Context) : DiskPersistence {

    companion object {
        const val PREFERENCE_FILE = "idealista_challenge"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)

    @Suppress("UNCHECKED_CAST")
    override fun <T> getValue(key: PersistenceKey<T>, default: T): T {
        return when (key) {
            PersistenceKey.FavoriteAds -> {
                (default as? Set<String>)?.let {
                    sharedPreferences.getStringSet(key.toStringKey(), it)
                } as T
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> insert(key: PersistenceKey<T>, value: T) {
        when (key) {
            is PersistenceKey.FavoriteAds -> {
                (value as? Set<String>)?.let {
                    sharedPreferences.edit().putStringSet(key.toStringKey(), it).apply()
                }
            }
        }
    }

    override fun clear(key: PersistenceKey<*>) {
        sharedPreferences.edit().remove(key.toStringKey()).apply()
    }

    override fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

}

