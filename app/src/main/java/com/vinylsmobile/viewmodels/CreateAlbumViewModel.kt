package com.vinylsmobile.viewmodels

import android.app.Application
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.R
import com.vinylsmobile.model.Album
import com.vinylsmobile.repository.AlbumRepository
import kotlinx.coroutines.launch

class CreateAlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AlbumRepository = AlbumRepository(
        application,
        com.vinylsmobile.database.VinylRoomDatabase.getDatabase(application).albumsDao()
    )

    val name = MutableLiveData<String>()
    val cover = MutableLiveData<String>()
    val releaseDate = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val genre = MutableLiveData<Int>()
    val recordLabel = MutableLiveData<Int>()

    private val _albumSaved = MutableLiveData<Boolean>()
    val albumSaved: LiveData<Boolean> get() = _albumSaved
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
       genre.value = 0
         recordLabel.value = 0
    }

    fun saveAlbum() {
        if (!validateFields()) return

        val genreString = getApplication<Application>().resources.getStringArray(R.array.genres)[genre.value ?: 0]
        val recordLabelString = getApplication<Application>().resources.getStringArray(R.array.record_labels)[recordLabel.value ?: 0]
        val album = Album(
            id = null,
            name = name.value ?: "",
            cover = cover.value ?: "",
            releaseDate = releaseDate.value ?: "",
            description = description.value ?: "",
            genre = genreString,
            recordLabel = recordLabelString,
            comments = null
        )
        // Save the album to the database or repository
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val savedAlbum = repository.saveAlbum(album)
                _albumSaved.value = true
                Toast.makeText(getApplication(), "Album guardado", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("ViewModel", "Error saving albums", e)
                Toast.makeText(getApplication(), "Error guardando album", Toast.LENGTH_SHORT).show()
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun isValidCoverUrl(url: String?): Boolean {
        return url != null && Patterns.WEB_URL.matcher(url).matches()
    }

    private fun validateField(field: String?, fieldName: String): Boolean {
        if (field.isNullOrEmpty()) {
            Toast.makeText(getApplication(), "$fieldName is required", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateFields(): Boolean {
        if (!isValidCoverUrl(cover.value)) {
            Toast.makeText(getApplication(), "Invalid cover URL", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!validateField(name.value, "Name")) return false
        if (!validateField(releaseDate.value, "Release date")) return false
        if (genre.value == null) {
            Toast.makeText(getApplication(), "Genre is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (recordLabel.value == null) {
            Toast.makeText(getApplication(), "Record label is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!validateField(description.value, "Description")) return false
        return true
    }

    fun onGenreSelected(position: Int) {
        genre.value = position
    }

    fun onRecordLabelSelected(position: Int) {
        recordLabel.value = position
    }


    class CreateAlbumViewModelFactory(private val application: android.app.Application)  : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateAlbumViewModel::class.java)) {
                return CreateAlbumViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}