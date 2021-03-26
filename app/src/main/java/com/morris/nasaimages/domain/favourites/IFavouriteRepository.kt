package com.morris.nasaimages.domain.favourites

import androidx.lifecycle.LiveData
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.favourites.Favourite
import kotlinx.coroutines.flow.Flow

interface IFavouriteRepository {

    //suspend fun getFavourites(): Resource<List<Favourite>>

    suspend fun getFavourites(): Resource<List<Favourite>>

    suspend fun getFavouritesByTitle(titleQuery: String): List<Favourite>

    suspend fun saveFavourite(favourite: Favourite)

    suspend fun deleteFavourite(favourite: Favourite)
}