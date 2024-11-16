package com.vinylsmobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinylsmobile.repository.CollectorRepository

class CollectorViewModelFactory(private val repository: CollectorRepository) : ViewModelProvider.Factory {
   /* override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
            return CollectorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }*/
}