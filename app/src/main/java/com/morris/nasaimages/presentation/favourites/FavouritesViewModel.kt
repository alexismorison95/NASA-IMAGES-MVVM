package com.morris.nasaimages.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.favourites.Favourite
import com.morris.nasaimages.domain.favourites.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel(private val repository: FavouriteRepository) : ViewModel() {


    fun loadFavouritesLiveData() =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {

            emit(Resource.Loading())

            try {
                emitSource(repository.getFavouritesLiveData().map { Resource.Success(it) })
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    fun saveFavourite(favourite: Favourite) {

        viewModelScope.launch {

            repository.saveFavourite(favourite)
        }
    }

    fun deleteFavourite(favourite: Favourite) {

        viewModelScope.launch {

            repository.deleteFavourite(favourite)
        }
    }
}