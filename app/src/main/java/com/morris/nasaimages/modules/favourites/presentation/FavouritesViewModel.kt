package com.morris.nasaimages.modules.favourites.presentation

import androidx.lifecycle.*
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.modules.favourites.data.model.Favourite
import com.morris.nasaimages.modules.favourites.repository.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class FavouritesViewModel(private val repository: FavouriteRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()

    fun setQuerySearch(query: String) {
        _query.value = query
    }


    private val _favouritesData = MutableLiveData<List<Favourite>?>()
    val favouritesData: LiveData<List<Favourite>?> = _favouritesData

    private val _isLoadig = MutableLiveData<Boolean>()
    val isLoadig: LiveData<Boolean> = _isLoadig

    private val _onMessageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError


    init {
        loadFavourites()
    }

    val fetchFavourites = _query.distinctUntilChanged().switchMap { query ->

        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {

            emit(repository.getFavouritesByTitle(query))
        }
    }

    fun loadFavourites() {

        viewModelScope.launch {

            when (val result = repository.getFavourites()) {

                is Resource.Loading -> {
                    _isLoadig.value = true
                }
                is Resource.Failure -> {

                    _isLoadig.value = false
                    _onMessageError.value = result.exception.message.toString()
                }
                is Resource.Success -> {

                    _isLoadig.value = false
                    _favouritesData.value = result.data
                }
            }
        }
    }


    fun saveFavourite(favourite: Favourite) {

        viewModelScope.launch {

            repository.saveFavourite(favourite)

            loadFavourites()
        }
    }

    fun deleteFavourite(favourite: Favourite) {

        viewModelScope.launch {

            repository.deleteFavourite(favourite)

            loadFavourites()
        }
    }
}