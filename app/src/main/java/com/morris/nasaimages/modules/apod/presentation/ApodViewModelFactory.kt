package com.morris.nasaimages.modules.apod.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.morris.nasaimages.modules.apod.repository.IApodRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ApodViewModelFactory(private val repository: IApodRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return modelClass.getConstructor(IApodRepository::class.java).newInstance(repository)
    }
}