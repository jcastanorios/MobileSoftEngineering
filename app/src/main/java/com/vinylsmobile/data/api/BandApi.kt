package com.vinylsmobile.data.api

import com.vinylsmobile.model.Band
import retrofit2.http.GET
import retrofit2.http.Path

interface BandApi {
    @GET("bands")
    suspend fun getBandList():List<Band>

    @GET("bands/{id}")
    suspend fun getBand(@Path("id") musicianID: Int): Band
}
