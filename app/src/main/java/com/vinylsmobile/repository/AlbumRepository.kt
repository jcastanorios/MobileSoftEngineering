package com.vinylsmobile.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.vinylsmobile.database.dao.AlbumsDao
import com.vinylsmobile.database.dao.CollectorsDao
import com.vinylsmobile.service.AlbumService
import com.vinylsmobile.model.Album
import com.vinylsmobile.model.Collector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository(private val application: Application, private val albumsDao: AlbumsDao) {

    private val albumService = AlbumService.getInstance()

    suspend fun getAlbumList(limit: Int): List<Album> = withContext(Dispatchers.IO) {


        var cached = albumsDao.getAlbums(limit)
        if (cached.size == 2 && limit> 2 ) { //Estan consultando más de los albumnes de la cache, se limipia la cache
            albumsDao.clearCache()
            cached = albumsDao.getAlbums(limit)
        }


        if (cached.isNullOrEmpty()) {
            // Verificar el estado de la red
            if (!isNetworkAvailable(application)) {
                return@withContext emptyList()
            } else {
                // Obtener los datos de la red
                val albumsFromNetwork = albumService.getAlbums(limit)
                // Guardar en la base de datos local
                albumsDao.insertAll(albumsFromNetwork)
                return@withContext albumsFromNetwork
            }
        } else {
            return@withContext cached
        }
    }




    suspend fun getAlbumItem(id: Int): Album {
        return albumService.getAlbum(id)
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
