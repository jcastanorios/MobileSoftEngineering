package com.vinylsmobile.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinylsmobile.model.IPerformer
import com.vinylsmobile.model.Musician
import com.vinylsmobile.repository.PerformerRepository
import kotlinx.coroutines.launch

class PerformerDetailViewModel(private val repository: PerformerRepository) : ViewModel() {
    private val _performer = MutableLiveData<IPerformer>()
    val performer: LiveData<IPerformer> get() = _performer

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
}