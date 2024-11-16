package com.vinylsmobile.service

import com.vinylsmobile.data.api.AlbumApi
import com.vinylsmobile.model.Album
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumService private constructor() {

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://backvynils-q6yc.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private val albumApi: AlbumApi by lazy {
        createRetrofit().create(AlbumApi::class.java)
    }

    companion object {
        @Volatile
        private var instance: AlbumService? = null

        fun getInstance(): AlbumService = instance ?: synchronized(this) {
            instance ?: AlbumService().also { instance = it }
        }
    }

    suspend fun getAlbums(limit: Int): List<Album> {
        return albumApi.getAlbumList()
            .sortedBy { it.name }
            .take(limit)
    }

    suspend fun getAlbum(id: Int): Album {
        return albumApi.getAlbum(id)
    }
}
