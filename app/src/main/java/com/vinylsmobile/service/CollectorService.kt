package com.vinylsmobile.service

import com.vinylsmobile.data.api.CollectorApi
import com.vinylsmobile.model.Collector
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CollectorService private constructor() {

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://backvynils-q6yc.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val collectorApi: CollectorApi by lazy {
        createRetrofit().create(CollectorApi::class.java)
    }

    companion object {
        @Volatile
        private var instance: CollectorService? = null

        fun getInstance(): CollectorService =
            instance ?: synchronized(this) {
                instance ?: CollectorService().also { instance = it }
            }
    }

    suspend fun getCollectors(): List<Collector> {
        return collectorApi.getCollectorsList()
    }

    suspend fun getCollector(id: Int): Collector {
        return collectorApi.getCollector(id)
    }
}