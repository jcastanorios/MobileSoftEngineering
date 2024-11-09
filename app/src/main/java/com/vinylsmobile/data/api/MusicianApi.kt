package com.vinylsmobile.data.api

import com.vinylsmobile.model.Musician
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicianApi {
    @GET("musicians")
    suspend fun getMusicianList():List<Musician>

    @GET("musicians/{id}")
    suspend fun getMusician(@Path("id") musicianID: Int):Musician
}
