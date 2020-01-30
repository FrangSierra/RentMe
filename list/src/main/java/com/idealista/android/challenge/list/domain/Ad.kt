package com.idealista.android.challenge.list.domain

import com.idealista.android.challenge.core.model.Operation
import com.idealista.android.challenge.core.model.Typology
import com.idealista.android.challenge.core.model.entity.AdEntity

data class Ad(
    val id: String,
    val thumbnail: String,
    val price: Double,
    val typology: Typology,
    val detailUrl: String,
    val operation: Operation,
    val district: String,
    val size: Int,
    val rooms: Int,
    val address: String,
    val province: String,
    val bathrooms: Int,
    val exterior: Boolean,
    val neighborhood: String
)

fun AdEntity.toDomain() =
    Ad(
        id = propertyCode ?: "",
        thumbnail = multimedia?.images?.get(0)?.url ?: "",
        price = price ?: 0.0,
        typology = Typology.from(propertyType ?: ""),
        detailUrl = detailUrl ?: "",
        operation = Operation.from(operation ?: ""),
        district = district ?: "",
        size = size ?: 0,
        rooms = rooms ?: 0,
        address = address ?: "",
        province = province ?: "",
        bathrooms = bathrooms ?: 0,
        exterior = exterior ?: false,
        neighborhood = neighborhood ?: ""
    )
