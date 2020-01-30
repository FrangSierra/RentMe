package com.idealista.android.challenge.core.api

import com.idealista.android.challenge.core.model.entity.AdEntity
import com.idealista.android.challenge.core.model.entity.ListEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/bins/93tqm")
    fun list(): Call<ListEntity>

    @GET("/bins/93tqm")
    fun ad(@Query("propertyCode") adId: String): Call<AdEntity>
}