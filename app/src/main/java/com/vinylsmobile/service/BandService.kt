package com.vinylsmobile.service

import com.vinylsmobile.data.api.BandApi
import com.vinylsmobile.model.Band
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BandService private constructor() {

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://backvynils-q6yc.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val bandApi: BandApi by lazy {
        createRetrofit().create(BandApi::class.java)
    }

    companion object {
        @Volatile
        private var instance: BandService? = null

        fun getInstance(): BandService =
            instance ?: synchronized(this) {
                instance ?: BandService().also { instance = it }
            }
    }

    suspend fun getBands(): List<Band> {
        return bandApi.getBandList()
    }

    suspend fun getBand(id: Int): Band {
        return bandApi.getBand(id)
    }
}
