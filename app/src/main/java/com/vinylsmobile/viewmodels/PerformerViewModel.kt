package com.vinylsmobile.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.model.Musician
import kotlinx.coroutines.launch
import com.vinylsmobile.repository.MusicianRepository

class PerformerViewModel(private val repository: MusicianRepository) : ViewModel() {
    private val _musicians = MutableLiveData<List<Musician>>()
    val musicians: LiveData<List<Musician>> get() = _musicians

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loadPerformers() {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val fetchedMusicians = repository.getMusicianList()
                _musicians.postValue(fetchedMusicians)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching performers", e)
                _errorMessage.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun resetErrorMessage() {
        _errorMessage.postValue(null)
    }
}
