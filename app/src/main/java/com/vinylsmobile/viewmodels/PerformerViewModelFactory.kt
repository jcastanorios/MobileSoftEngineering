package com.vinylsmobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinylsmobile.repository.MusicianRepository

class PerformerViewModelFactory(private val repository: MusicianRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerformerViewModel::class.java)) {
            return PerformerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}