package com.vinylsmobile.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.vinylsmobile.model.IPerformer
import com.vinylsmobile.service.BandService
import com.vinylsmobile.service.MusicianService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import com.vinylsmobile.database.dao.PerformersDao
import com.vinylsmobile.model.Performer
import com.vinylsmobile.model.PerformerType

class PerformerRepository(private val application: Application, private val performersDao: PerformersDao) {
    private val musicianService = MusicianService.getInstance()
    private val bandService = BandService.getInstance()

    suspend fun getPerformerList(limiteinicial: Int?): List<IPerformer> = withContext(Dispatchers.IO) {
        val limit = limiteinicial ?: Int.MAX_VALUE
 
        var cached = performersDao.getPerformers(limit)
        if (cached.size == 2 && limit> 2 ) { //Estan consultando más de los albumnes de la cache, se limpia la cache
            performersDao.clearCache()
            cached = performersDao.getPerformers(limit)
        }

        if (cached.isNullOrEmpty()) {
            // Verificar el estado de la red
            if (!isNetworkAvailable(application)) {
                return@withContext emptyList()
            } else {
                val bandsFromNetwork = bandService.getBands()
                val musiciansFromNetwork = musicianService.getMusicians()

                // Convertir resultados de red a Performer
                val performersFromNetwork = mutableListOf<Performer>()

                performersFromNetwork.addAll(
                    musiciansFromNetwork.map { musician ->
                        Performer(
                            id = musician.id,
                            name = musician.name,
                            image = musician.image,
                            description = musician.description,
                            type = PerformerType.ARTIST,
                            albums = musician.albums ?: emptyList()
                        )
                    }
                )
                performersFromNetwork.addAll(
                    bandsFromNetwork.map { band ->
                        Performer(
                            id = band.id,
                            name = band.name,
                            image = band.image,
                            description = band.description,
                            type = PerformerType.BAND,
                            albums = band.albums ?: emptyList()
                        )
                    }
                )

                // Aplicar límite y ordenar
                val sortedPerformers = performersFromNetwork.sortedBy { it.name }.take(limit)


                // Insertar en cache
                performersDao.insertAll(sortedPerformers)
                return@withContext sortedPerformers
            }
        } else {
            return@withContext cached
        }
    }

    suspend fun getPerformerItem(id: Int): IPerformer? {
        try {
            return musicianService.getMusician(id);
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
