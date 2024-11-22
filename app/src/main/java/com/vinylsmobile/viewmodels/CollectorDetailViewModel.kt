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

class CollectorDetailViewModel(application: Application) : AndroidViewModel(application) {
    // Crear el repositorio con el DAO desde la base de datos
    private val repository: CollectorRepository = CollectorRepository(
        application,
        com.vinylsmobile.database.VinylRoomDatabase.getDatabase(application).collectorsDao()
    )

    private val _collector = MutableLiveData<Collector>()
    val collector: LiveData<Collector> get() = _collector

    fun loadCollector(id: Int) {
        viewModelScope.launch {
            try {
                val fetchedCollectors = repository.getCollectorItem(id)
                _collector.postValue(fetchedCollectors)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching collector", e)
            }
        }
    }


    class CollectorDetailViewModelFactory(private val application: Application)  : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorDetailViewModel::class.java)) {
                return CollectorDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}