package com.morris.nasaimages.data.local.database

import androidx.room.*
import com.morris.nasaimages.data.model.database.FavouriteEntity

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    suspend fun getFavourites(): List<FavouriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavourite(favourite: FavouriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavouriteEntity)
}