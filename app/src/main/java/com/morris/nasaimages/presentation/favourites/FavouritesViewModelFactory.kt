package com.morris.nasaimages.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.morris.nasaimages.domain.favourites.FavouriteRepository

class FavouritesViewModelFactory(private val repository: FavouriteRepository) :ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return modelClass.getConstructor(FavouriteRepository::class.java).newInstance(repository)
    }
}