package com.vinylsmobile.repository

import com.vinylsmobile.model.Collector
import com.vinylsmobile.service.CollectorService

class CollectorRepository {
    private val collectorService = CollectorService.getInstance()

    suspend fun getCollectorsList(): List<Collector>{
        return  collectorService.getCollectors()
    }

    suspend fun getCollectorItem(id: Int): Collector{
        return collectorService.getCollector(id)
    }
}