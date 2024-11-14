package com.vinylsmobile.repository

import com.vinylsmobile.model.IPerformer
import com.vinylsmobile.service.BandService
import com.vinylsmobile.service.MusicianService
import retrofit2.HttpException

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
        try {
            return  musicianService.getMusician(id);
        } catch (e: Exception) {
            if (e is HttpException && e.code() == 404) {
                try {
                    val band = bandService.getBand(id);
                    return band;
                } catch (e: Exception) {
                    if (e is HttpException && e.code() == 404) {
                        return null
                    }
                }
            }
        }
        return null
    }
}
