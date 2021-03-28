package com.morris.nasaimages.modules.apod.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.morris.nasaimages.modules.apod.repository.ApodRepository

class ApodViewModelFactory(private val repository: ApodRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return modelClass.getConstructor(ApodRepository::class.java).newInstance(repository)
    }
}