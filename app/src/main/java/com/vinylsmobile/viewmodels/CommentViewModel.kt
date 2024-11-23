package com.vinylsmobile.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.database.VinylRoomDatabase
import com.vinylsmobile.model.Comment
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class CommentViewModel(application: Application) : AndroidViewModel(application) {
    private val commentDao = VinylRoomDatabase.getDatabase(application).commentDao()

    private val _commentSaved = MutableLiveData<Boolean>()
    val commentSaved: LiveData<Boolean> get() = _commentSaved


    fun saveComment(comment: Comment) {
        viewModelScope.launch {
            try {
                commentDao.insertComment(comment)
                _commentSaved.postValue(true)
            } catch (e: Exception) {
                _commentSaved.postValue(false)
                e.printStackTrace()
            }
        }
    }

    fun getCommentsForAlbum(albumId: Int): LiveData<List<Comment>> {
        val commentsLiveData = MutableLiveData<List<Comment>>()
        viewModelScope.launch {
            try {
                val comments = commentDao.getCommentsForAlbum(albumId)
                commentsLiveData.postValue(comments)
            } catch (e: Exception) {
                commentsLiveData.postValue(emptyList())
                e.printStackTrace()
            }
        }
        return commentsLiveData
    }
}
