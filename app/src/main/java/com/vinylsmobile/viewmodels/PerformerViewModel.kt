package com.vinylsmobile.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.model.IPerformer
import com.vinylsmobile.repository.PerformerRepository
import kotlinx.coroutines.launch

class PerformerViewModel(application: Application) : AndroidViewModel(application) {

    // Crear el repositorio con el DAO desde la base de datos
    private val repository: PerformerRepository = PerformerRepository(
        application,
        com.vinylsmobile.database.VinylRoomDatabase.getDatabase(application).performersDao()
    )

    private val _performers = MutableLiveData<List<IPerformer>>()
    val performers: LiveData<List<IPerformer>> get() = _performers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loadPerformers(limit: Int? = null) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val fetchedPerformers = repository.getPerformerList(limit)
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

    class PerformerViewModelFactory(private val application: Application)  : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerViewModel::class.java)) {
                return PerformerViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }





}
