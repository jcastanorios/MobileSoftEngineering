package com.vinylsmobile.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.vinylsmobile.model.Album
import com.vinylsmobile.repository.AlbumRepository

class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    fun loadAlbums() {
        viewModelScope.launch {
            try {
                val fetchedAlbums = repository.getAlbumList()
                _albums.postValue(fetchedAlbums)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching albums", e)
            }
        }
    }
}
