package com.vinylsmobile.data.api

import com.vinylsmobile.model.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumApi {
    @GET("albums")
    suspend fun getAlbumList():List<Album>

    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") albumID: Int):Album
}
