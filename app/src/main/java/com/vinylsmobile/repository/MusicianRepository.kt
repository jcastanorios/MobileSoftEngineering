package com.vinylsmobile.repository

import com.vinylsmobile.service.AlbumService
import com.vinylsmobile.model.Album
import com.vinylsmobile.model.Musician
import com.vinylsmobile.service.MusicianService

class MusicianRepository {
    private val musicianService = MusicianService.getInstance()

    suspend fun getMusicianList(): List<Musician> {
        return musicianService.getMusicians()
    }

    suspend fun getMusicianItem(id: Int): Musician {
        return musicianService.getMusician(id)
    }
}
