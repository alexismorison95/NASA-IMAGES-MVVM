package com.morris.nasaimages.data.local.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.local.AppDatabase
import com.morris.nasaimages.data.model.favourites.Favourite
import com.morris.nasaimages.data.model.favourites.asFavourite
import com.morris.nasaimages.data.model.favourites.asFavouriteEntity
import com.morris.nasaimages.data.model.favourites.asFavouriteList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouriteDataSource(private val appDatabase: AppDatabase) {

    suspend fun getFavourites(): List<Favourite> {

        return appDatabase.favouriteDao().getFavourites().map { it.asFavourite() }
    }

    suspend fun getFavouritesByTitle(titleQuery: String): List<Favourite> {

        return appDatabase.favouriteDao().getFavouritesByTitle(titleQuery).map { it.asFavourite() }
    }

    suspend fun saveFavourite(favourite: Favourite) {

        appDatabase.favouriteDao().saveFavourite(favourite.asFavouriteEntity())
    }

    suspend fun deleteFavourite(favourite: Favourite) {

        appDatabase.favouriteDao().deleteFavorite(favourite.asFavouriteEntity())
    }
}