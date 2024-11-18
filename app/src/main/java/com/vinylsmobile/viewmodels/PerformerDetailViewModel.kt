package com.vinylsmobile.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.model.IPerformer
import com.vinylsmobile.repository.PerformerRepository
import kotlinx.coroutines.launch

class PerformerDetailViewModel(application: Application) : AndroidViewModel(application) {

    // Crear el repositorio con el DAO desde la base de datos
    private val repository: PerformerRepository = PerformerRepository(
        application,
        com.vinylsmobile.database.VinylRoomDatabase.getDatabase(application).performersDao()
    )

    private val _performer = MutableLiveData<IPerformer?>()
    val performer: MutableLiveData<IPerformer?> get() = _performer

    fun loadPerformer(id: Int) {
        viewModelScope.launch {
            try {
                val fetchedPerformer = repository.getPerformerItem(id)
                _performer.postValue(fetchedPerformer)

            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching performer", e)
            }
        }
    }

    class PerformerDetailViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerDetailViewModel::class.java)) {
                return PerformerDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}