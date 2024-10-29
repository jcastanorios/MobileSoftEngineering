package com.vinylsmobile.data.repository

import android.content.Context
import com.android.volley.VolleyError
import com.vinylsmobile.data.model.Album
import com.vinylsmobile.network.NetworkServiceAdapter

class AlbumRepository(private val context: Context) {
    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(context)

    fun getAlbumList(onComplete: (List<Album>) -> Unit, onError: (VolleyError) -> Unit) {
        networkServiceAdapter.getAlbums(
            onComplete = { albumList ->
                onComplete(albumList)
            },
            onError = { error ->
                onError(error) 
            }
        )
    }
}