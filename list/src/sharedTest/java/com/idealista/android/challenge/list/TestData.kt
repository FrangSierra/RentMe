package com.idealista.android.challenge.list

import com.idealista.android.challenge.core.model.Operation
import com.idealista.android.challenge.core.model.Typology
import com.idealista.android.challenge.list.domain.Ad

object TestData {
    val sampleAd = Ad(
        id = "112",
        thumbnail = "",
        price = 800.0,
        district = "Moncloa",
        size = 85,
        rooms = 4,
        address = "Joaquin Maria Lopez 24",
        bathrooms = 3,
        exterior = true,
        neighborhood = "Arguelles",
        operation = Operation.Rent,
        typology = Typology.Flat,
        province = "Madrid",
        detailUrl = ""
    )

    val sampleAdList = listOf(sampleAd)
}