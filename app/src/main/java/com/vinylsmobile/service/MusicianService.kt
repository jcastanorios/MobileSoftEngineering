package com.vinylsmobile.service

import com.vinylsmobile.data.api.MusicianApi
import com.vinylsmobile.model.Musician
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MusicianService private constructor() {

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://backvynils-q6yc.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val musicianApi: MusicianApi by lazy {
        createRetrofit().create(MusicianApi::class.java)
    }

    companion object {
        @Volatile
        private var instance: MusicianService? = null

        fun getInstance(): MusicianService =
            instance ?: synchronized(this) {
                instance ?: MusicianService().also { instance = it }
            }
    }

    suspend fun getMusicians(): List<Musician> {
        return musicianApi.getMusicianList()
    }

    suspend fun getMusician(id: Int): Musician {
        return musicianApi.getMusician(id)
    }
}
