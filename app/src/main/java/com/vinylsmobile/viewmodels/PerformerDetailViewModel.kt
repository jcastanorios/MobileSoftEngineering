package com.vinylsmobile.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.model.Album
import com.vinylsmobile.repository.AlbumRepository
import kotlinx.coroutines.launch

class PerformerDetailViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> get() = _album

    fun loadAlbum(id: Int) {
        viewModelScope.launch {
            try {
                val fetchedAlbums = repository.getAlbumItem(id)
                _album.postValue(fetchedAlbums)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching album", e)
            }
        }
    }
}