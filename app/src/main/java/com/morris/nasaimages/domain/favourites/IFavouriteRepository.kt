package com.morris.nasaimages.domain.favourites

import androidx.lifecycle.LiveData
import com.morris.nasaimages.data.model.favourites.Favourite

interface IFavouriteRepository {

    //suspend fun getFavourites(): Resource<List<Favourite>>

    fun getFavouritesLiveData(): LiveData<List<Favourite>>

    suspend fun saveFavourite(favourite: Favourite)

    suspend fun deleteFavourite(favourite: Favourite)
}