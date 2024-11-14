package com.vinylsmobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinylsmobile.repository.PerformerRepository

class PerformerDetailViewModelFactory(private val repository: PerformerRepository ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerformerDetailViewModel::class.java)) {
            return PerformerDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}