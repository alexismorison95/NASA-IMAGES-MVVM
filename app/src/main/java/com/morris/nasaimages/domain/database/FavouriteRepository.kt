package com.morris.nasaimages.domain.database

import androidx.lifecycle.LiveData
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.local.database.FavouriteDataSource
import com.morris.nasaimages.data.model.database.Favourite

class FavouriteRepository(private val dataSource: FavouriteDataSource) : IFavouriteRepository {

    override suspend fun getFavourites(): Resource<List<Favourite>> {

        return dataSource.getFavourites()
    }

    override fun getFavouritesLiveData(): LiveData<List<Favourite>> {

        return dataSource.getFavouritesLiveData()
    }

    override suspend fun saveFavourite(favourite: Favourite) {

        dataSource.saveFavourite(favourite)
    }

    override suspend fun deleteFavourite(favourite: Favourite) {

        dataSource.deleteFavourite(favourite)
    }
}