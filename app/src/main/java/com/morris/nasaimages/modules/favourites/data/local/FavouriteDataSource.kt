package com.morris.nasaimages.modules.favourites.data.local

import com.morris.nasaimages.application.AppDatabase
import com.morris.nasaimages.modules.favourites.data.model.Favourite
import com.morris.nasaimages.modules.favourites.data.model.asFavourite
import com.morris.nasaimages.modules.favourites.data.model.asFavouriteEntity

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