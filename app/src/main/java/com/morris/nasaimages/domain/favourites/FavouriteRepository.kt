package com.morris.nasaimages.domain.favourites

import androidx.lifecycle.LiveData
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.local.favourites.FavouriteDataSource
import com.morris.nasaimages.data.model.favourites.Favourite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class FavouriteRepository(private val dataSource: FavouriteDataSource) : IFavouriteRepository {


    override suspend fun getFavourites(): Resource<List<Favourite>> {

        return Resource.Success(dataSource.getFavourites())
    }

    override suspend fun getFavouritesByTitle(titleQuery: String): List<Favourite> {

        return dataSource.getFavouritesByTitle(titleQuery)
    }

    override suspend fun saveFavourite(favourite: Favourite) {

        dataSource.saveFavourite(favourite)
    }

    override suspend fun deleteFavourite(favourite: Favourite) {

        dataSource.deleteFavourite(favourite)
    }
}