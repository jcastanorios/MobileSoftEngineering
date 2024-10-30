package com.vinylsmobile.network

import com.vinylsmobile.data.api.AlbumApi
import com.vinylsmobile.data.model.Album
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class NetworkServiceAdapter private constructor() {

    private val api: AlbumApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://backvynils-q6yc.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumApi::class.java)
    }

    companion object {
        @Volatile
        private var instance: NetworkServiceAdapter? = null

        fun getInstance(): NetworkServiceAdapter =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter().also { instance = it }
            }
    }

    suspend fun getAlbums(): List<Album> {
        return api.getAlbumList()
    }
}
