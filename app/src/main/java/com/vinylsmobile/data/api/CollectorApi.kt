package com.vinylsmobile.data.api

import com.vinylsmobile.model.Collector
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectorApi {
    @GET("collectors")
    suspend fun getCollectorsList():List<Collector>

    @GET("collectors/{id}")
    suspend fun getCollector(@Path("id") collectorID: Int): Collector
}