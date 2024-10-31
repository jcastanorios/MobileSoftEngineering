package com.vinylsmobile.data.repository

import com.vinylsmobile.network.NetworkServiceAdapter
import com.vinylsmobile.data.model.Album

class AlbumRepository {
    private val networkService = NetworkServiceAdapter.getInstance()

    suspend fun getAlbumList(): List<Album> {
        return networkService.getAlbums()
    }
}
