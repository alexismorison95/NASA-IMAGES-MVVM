package com.morris.nasaimages.modules.library.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.morris.nasaimages.modules.library.repository.ILibraryRepository

class LibraryviewModelFactory(private val repository: ILibraryRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return modelClass.getConstructor(ILibraryRepository::class.java).newInstance(repository)
    }
}