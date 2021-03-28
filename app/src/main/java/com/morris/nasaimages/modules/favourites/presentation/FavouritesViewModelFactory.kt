package com.morris.nasaimages.modules.favourites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.morris.nasaimages.modules.favourites.repository.FavouriteRepository

class FavouritesViewModelFactory(private val repository: FavouriteRepository) :ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return modelClass.getConstructor(FavouriteRepository::class.java).newInstance(repository)
    }
}