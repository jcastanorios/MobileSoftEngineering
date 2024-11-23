package com.vinylsmobile.data.api

import com.vinylsmobile.model.Album
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumApi {
    @GET("albums")
    suspend fun getAlbumList():List<Album>

    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") albumID: Int):Album

    @POST("albums")
    suspend fun postAlbum(@Body album: Album):Album
}
