package com.vinylsmobile.network

import com.vinylsmobile.data.api.AlbumApi
import com.vinylsmobile.data.model.Album
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class NetworkServiceAdapter private constructor() {

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://backvynils-q6yc.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val albumApi: AlbumApi by lazy {
        createRetrofit().create(AlbumApi::class.java)
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
        return albumApi.getAlbumList()
    }
}
