package com.morris.nasaimages.data.local.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.database.Favourite
import com.morris.nasaimages.data.model.database.asFavourite
import com.morris.nasaimages.data.model.database.asFavouriteEntity
import com.morris.nasaimages.data.model.database.asFavouriteList

class FavouriteDataSource(private val appDatabase: AppDatabase) {

    suspend fun getFavourites(): Resource<List<Favourite>> {

        return Resource.Success(appDatabase.favouriteDao().getFavourites().map { it.asFavourite() })
    }

    fun getFavouritesLiveData(): LiveData<List<Favourite>> {

        return appDatabase.favouriteDao().getFavouritesLiveData().map { it.asFavouriteList() }
    }

    suspend fun saveFavourite(favourite: Favourite) {

        appDatabase.favouriteDao().saveFavourite(favourite.asFavouriteEntity())
    }

    suspend fun deleteFavourite(favourite: Favourite) {

        appDatabase.favouriteDao().deleteFavorite(favourite.asFavouriteEntity())
    }
}