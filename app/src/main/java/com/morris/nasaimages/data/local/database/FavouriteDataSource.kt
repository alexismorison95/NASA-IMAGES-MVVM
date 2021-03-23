package com.morris.nasaimages.data.local.database

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.database.Favourite
import com.morris.nasaimages.data.model.database.asFavourite
import com.morris.nasaimages.data.model.database.asFavouriteEntity

class FavouriteDataSource(private val appDatabase: AppDatabase) {

    suspend fun getFavourites(): Resource<List<Favourite>> {

        return Resource.Success(appDatabase.favouriteDao().getFavourites().map { it.asFavourite() })
    }

    suspend fun saveFavourite(favourite: Favourite) {

        appDatabase.favouriteDao().saveFavourite(favourite.asFavouriteEntity())
    }

    suspend fun deleteFavourite(favourite: Favourite) {

        appDatabase.favouriteDao().deleteFavorite(favourite.asFavouriteEntity())
    }
}