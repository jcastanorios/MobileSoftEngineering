package com.vinylsmobile.data.api

import com.vinylsmobile.model.Album
import retrofit2.http.GET

interface AlbumApi {
    @GET("albums")
    suspend fun getAlbumList():List<Album>
}