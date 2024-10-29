package com.vinylsmobile.ui.viewmodels
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vinylsmobile.data.model.Album
import com.vinylsmobile.data.repository.AlbumRepository
import com.android.volley.VolleyError

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumsRepository = AlbumRepository(application) // Usa application para inicializar AlbumRepository

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _eventNetworkError = MutableLiveData<Boolean>()
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        _isLoading.value = true
        albumsRepository.getAlbumList(
            onComplete = { albumList ->
                _albums.postValue(albumList)
                _isLoading.postValue(false)
                _eventNetworkError.postValue(false)
            },
            onError = { error ->
                _isLoading.postValue(false)
                _eventNetworkError.postValue(true)
            }
        )
    }
}
