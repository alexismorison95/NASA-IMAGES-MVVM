package com.morris.nasaimages.modules.favourites.repository

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.modules.favourites.data.local.FavouriteDataSource
import com.morris.nasaimages.modules.favourites.data.model.Favourite
import kotlinx.coroutines.ExperimentalCoroutinesApi

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