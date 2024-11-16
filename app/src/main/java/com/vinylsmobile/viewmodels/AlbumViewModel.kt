package com.vinylsmobile.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.vinylsmobile.model.Album
import com.vinylsmobile.repository.AlbumRepository


class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    // Crear el repositorio con el DAO desde la base de datos
    private val repository: AlbumRepository = AlbumRepository(
        application,
        com.vinylsmobile.database.VinylRoomDatabase.getDatabase(application).albumsDao()
    )

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loadAlbums(limit: Int? = null) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val fetchedAlbums = repository.getAlbumList(limit = limit ?: Int.MAX_VALUE)
                _albums.postValue(fetchedAlbums)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching albums", e)
                _errorMessage.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun resetErrorMessage() {
        _errorMessage.postValue(null)
    }

    class AlbumViewModelFactory(private val application: Application)  : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                return AlbumViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
