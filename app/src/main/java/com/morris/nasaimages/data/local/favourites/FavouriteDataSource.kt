package com.morris.nasaimages.data.local.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.morris.nasaimages.data.local.AppDatabase
import com.morris.nasaimages.data.model.favourites.Favourite
import com.morris.nasaimages.data.model.favourites.asFavouriteEntity
import com.morris.nasaimages.data.model.favourites.asFavouriteList

class FavouriteDataSource(private val appDatabase: AppDatabase) {

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