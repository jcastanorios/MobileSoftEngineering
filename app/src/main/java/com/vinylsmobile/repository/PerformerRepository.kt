package com.vinylsmobile.repository

import com.vinylsmobile.model.IPerformer
import com.vinylsmobile.service.BandService
import com.vinylsmobile.service.MusicianService

class PerformerRepository {
    private val musicianService = MusicianService.getInstance()
    private val bandService = BandService.getInstance()

    suspend fun getPerformerList(): List<IPerformer> {
        val bands = bandService.getBands()
        val musicians = musicianService.getMusicians()
        val performers = mutableListOf<IPerformer>()
        performers.addAll(musicians)
        performers.addAll(bands)
        return performers.sortedBy { it.name }
    }

    suspend fun getPerformerItem(id: Int): IPerformer? {
        val musician = musicianService.getMusician(id)
        val band = bandService.getBand(id)
        if (musician.id == id) {
            return musician
        }
        if (band.id == id) {
            return band
        }
        return null
    }
}
