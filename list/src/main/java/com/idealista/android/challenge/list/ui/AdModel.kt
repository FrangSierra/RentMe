package com.idealista.android.challenge.list.ui

import android.os.Parcelable
import com.idealista.android.challenge.core.CoreModule
import com.idealista.android.challenge.core.R
import com.idealista.android.challenge.core.model.Operation
import com.idealista.android.challenge.core.model.Typology
import com.idealista.android.challenge.core.model.string
import com.idealista.android.challenge.list.domain.Ad
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdModel(
    val id: String,
    val thumbnail: String,
    val price: String,
    val title: String,
    val district: String,
    val size: String,
    val rooms: String,
    val address: String,
    val bathrooms: String,
    val exterior: Boolean,
    val neighborhood: String,
    val isFavorite: Boolean
) : Parcelable

fun Ad.toModel(favorite: Boolean) =
    AdModel(
        id = id,
        thumbnail = thumbnail,
        price = formatPrice(price),
        title = formatTitle(typology, operation),
        district = district,
        size = formatSize(size),
        rooms = formatRooms(rooms),
        address = formatAddress(address, province),
        bathrooms = formatBathrooms(bathrooms),
        exterior = exterior,
        neighborhood = neighborhood,
        isFavorite = favorite
    )

private fun formatAddress(adress: String, province: String) = "$adress, $province"
private fun formatSize(size: Int) = "$size m2"
private fun formatBathrooms(bathrooms: Int) = CoreModule.stringsProvider.quantityString(
    R.plurals.bathrooms_size_format, bathrooms
)

private fun formatRooms(rooms: Int) = CoreModule.stringsProvider.quantityString(
    R.plurals.rooms_size_format, rooms
)
private fun formatPrice(price: Double) = "$price €"
private fun formatTitle(typology: Typology, operation: Operation) =
    CoreModule.stringsProvider.string(
        R.string.typology_at_operation,
        typology.string(),
        operation.string().toLowerCase()
    )