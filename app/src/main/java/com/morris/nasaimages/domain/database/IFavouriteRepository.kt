package com.morris.nasaimages.domain.database

import androidx.lifecycle.LiveData
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.database.Favourite

interface IFavouriteRepository {

    suspend fun getFavourites(): Resource<List<Favourite>>

    fun getFavouritesLiveData(): LiveData<List<Favourite>>

    suspend fun saveFavourite(favourite: Favourite)

    suspend fun deleteFavourite(favourite: Favourite)
}