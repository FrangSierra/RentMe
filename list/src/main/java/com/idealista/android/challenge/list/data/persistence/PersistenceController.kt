package com.idealista.android.challenge.list.data.persistence

import com.idealista.android.challenge.list.data.persistence.PersistenceKey.FavoriteAds

class PersistenceController(private val diskPersistence: DiskPersistence) {
    companion object {
        private const val FAVORITE_ADS_KEY = "favorites"
    }

    fun markAdAsFavorite(adId: String): Set<String> {
        val currentFavoriteAds = diskPersistence.getValue(FavoriteAds, emptySet())
        val favoriteAds =
            if (currentFavoriteAds.contains(adId)) currentFavoriteAds.minus(adId)
            else currentFavoriteAds.plus(adId)
        diskPersistence.insert(FavoriteAds, favoriteAds)
        return favoriteAds
    }

    fun getFavoriteAdIds(): Set<String> = diskPersistence.getValue(FavoriteAds, emptySet())

    fun clearFavorites() = diskPersistence.clear(key = FavoriteAds)
}
