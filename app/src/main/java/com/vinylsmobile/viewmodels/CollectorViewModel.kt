package com.vinylsmobile.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.model.Collector
import com.vinylsmobile.repository.CollectorRepository
import kotlinx.coroutines.launch

class CollectorViewModel(application: Application) : AndroidViewModel(application) {

    // Crear el repositorio con el DAO desde la base de datos
    private val repository: CollectorRepository = CollectorRepository(
        application,
        com.vinylsmobile.database.VinylRoomDatabase.getDatabase(application).collectorsDao()
    )

    private val _collectors = MutableLiveData<List<Collector>>()
    val collectors: LiveData<List<Collector>> get() = _collectors

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loadCollectors() {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val fetchedCollectors = repository.getCollectorsList()
                _collectors.postValue(fetchedCollectors)
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


    class CollectorViewModelFactory(private val application: Application)  : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                return CollectorViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}