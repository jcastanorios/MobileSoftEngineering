package com.vinylsmobile.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.model.Performer
import com.vinylsmobile.repository.PerformerRepository
import kotlinx.coroutines.launch

class PerformerDetailViewModel(private val repository: PerformerRepository) : ViewModel() {
    private val _album = MutableLiveData<Album>()
    val album: LiveData<Performer> get() = _album

    fun loadPerformer(id: Int) {
        viewModelScope.launch {
            try {
                val fetchedPerformer = repository.getPerformerItem(id)
                _album.postValue(fetchedPerformer)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching album", e)
            }
        }
    }
}