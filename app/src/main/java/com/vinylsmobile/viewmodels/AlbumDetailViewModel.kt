package com.vinylsmobile.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.model.Album
import com.vinylsmobile.model.Comment
import com.vinylsmobile.repository.AlbumRepository
import kotlinx.coroutines.launch

class AlbumDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AlbumRepository = AlbumRepository(
        application,
        com.vinylsmobile.database.VinylRoomDatabase.getDatabase(application).albumsDao()
    )

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> get() = _album

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments

    private val commentViewModel = CommentViewModel(application)


    fun loadAlbum(id: Int) {
        viewModelScope.launch {
            try {
                val fetchedAlbums = repository.getAlbumItem(id)
                _album.postValue(fetchedAlbums)
                _comments.postValue(fetchedAlbums.comments)
                loadCommentsForAlbum(fetchedAlbums.id)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching album", e)
            }
        }
    }

    private fun loadCommentsForAlbum(albumId: Int) {
        commentViewModel.getCommentsForAlbum(albumId).observeForever { comments ->
            _comments.postValue(comments)
        }
    }

    class AlbumDetailViewModelFactory(private val application: Application)  : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
                return AlbumDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }



}