package com.vinylsmobile.repository

import com.vinylsmobile.service.AlbumService
import com.vinylsmobile.model.Album

class AlbumRepository {
    private val albumService = AlbumService.getInstance()

    suspend fun getAlbumList(): List<Album> {
        return albumService.getAlbums()
    }

    suspend fun getAlbumItem(id: Int): Album {
        return albumService.getAlbum(id)
    }
}
