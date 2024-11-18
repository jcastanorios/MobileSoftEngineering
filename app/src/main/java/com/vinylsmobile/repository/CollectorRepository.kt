package com.vinylsmobile.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.vinylsmobile.database.dao.CollectorsDao
import com.vinylsmobile.model.Collector
import com.vinylsmobile.service.CollectorService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CollectorRepository(private val application: Application, private val collectorsDao: CollectorsDao) {

    private val collectorService = CollectorService.getInstance()


    suspend fun getCollectorsList(): List<Collector> = withContext(Dispatchers.IO) {
        // Consultar primero el caché local
        val cached = collectorsDao.getCollectors()
        if (cached.isNullOrEmpty()) {
            // Verificar el estado de la red
            if (!isNetworkAvailable(application)) {
                return@withContext emptyList()
            } else {
                // Obtener los datos de la red
                val collectorsFromNetwork = collectorService.getCollectors()
                // Guardar en la base de datos local
                collectorsDao.insertAll(collectorsFromNetwork)
                return@withContext collectorsFromNetwork
            }
        } else {
            return@withContext cached
        }
    }

    suspend fun getCollectorItem(id: Int): Collector{
        return collectorService.getCollector(id)
    }

    /*
    * Verificar si hay conexión a internet
     */
    private suspend fun isNetworkAvailable(context: Context): Boolean = withContext(Dispatchers.IO) {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return@withContext false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return@withContext false
            return@withContext networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } catch (e: Exception) {
            return@withContext false
        }
    }
}