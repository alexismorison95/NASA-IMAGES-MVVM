package com.morris.nasaimages.presentation.favourites

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.database.Favourite
import com.morris.nasaimages.domain.database.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel(private val repository: FavouriteRepository) : ViewModel() {

    fun loadFavourites() = liveData<Resource<List<Favourite>>>(Dispatchers.IO) {

        emit(Resource.Loading())

        try {
            emit(repository.getFavourites())
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