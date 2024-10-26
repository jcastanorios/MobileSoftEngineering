package com.vinylsmobile.data.repository

import com.vinylsmobile.data.api.AlbumApi
import com.vinylsmobile.data.model.Album

class AlbumRepository(private val api: AlbumApi) {
    suspend fun getAlbumList(): List<Album> {
        return api.getAlbumList()
    }
}