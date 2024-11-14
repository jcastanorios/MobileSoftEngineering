package com.vinylsmobile.repository

import com.vinylsmobile.service.AlbumService
import com.vinylsmobile.model.Album

class AlbumRepository {
    private val albumService = AlbumService.getInstance()

    suspend fun getAlbumList(limit: Int): List<Album> {
        return albumService.getAlbums(limit)
    }

    suspend fun getAlbumItem(id: Int): Album {
        return albumService.getAlbum(id)
    }
}
