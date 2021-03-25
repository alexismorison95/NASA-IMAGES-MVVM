package com.morris.nasaimages.domain.favourites

import androidx.lifecycle.LiveData
import com.morris.nasaimages.data.local.favourites.FavouriteDataSource
import com.morris.nasaimages.data.model.favourites.Favourite

class FavouriteRepository(private val dataSource: FavouriteDataSource) : IFavouriteRepository {


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