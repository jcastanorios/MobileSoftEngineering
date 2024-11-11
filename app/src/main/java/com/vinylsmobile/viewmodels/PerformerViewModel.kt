package com.vinylsmobile.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.model.IPerformer
import com.vinylsmobile.repository.PerformerRepository
import kotlinx.coroutines.launch

class PerformerViewModel(private val repository: PerformerRepository) : ViewModel() {
    private val _performers = MutableLiveData<List<IPerformer>>()
    val performers: LiveData<List<IPerformer>> get() = _performers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loadPerformers() {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val fetchedPerformers = repository.getPerformerList()
                _performers.postValue(fetchedPerformers)
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
