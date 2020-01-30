package com.idealista.android.challenge.core.model.entity

data class AdEntity(
    val propertyCode: String?,
    val thumbnail: String?,
    val price: Double?,
    val district: String?,
    val size: Int?,
    val rooms: Int?,
    val address: String?,
    val province: String?,
    val bathrooms: Int?,
    val exterior: Boolean?,
    val neighborhood: String?,
    val propertyType: String?,
    val operation: String?,
    val detailUrl: String?,
    val multimedia: MultimediasEntity?
)