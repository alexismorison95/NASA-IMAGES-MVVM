package com.morris.nasaimages.modules.favourites.repository

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.modules.favourites.data.model.Favourite

interface IFavouriteRepository {

    //suspend fun getFavourites(): Resource<List<Favourite>>

    suspend fun getFavourites(): Resource<List<Favourite>>

    suspend fun getFavouritesByTitle(titleQuery: String): List<Favourite>

    suspend fun saveFavourite(favourite: Favourite)

    suspend fun deleteFavourite(favourite: Favourite)
}