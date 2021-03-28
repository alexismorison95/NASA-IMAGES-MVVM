package com.morris.nasaimages.modules.favourites.data.local

import androidx.room.*
import com.morris.nasaimages.modules.favourites.data.model.FavouriteEntity

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites ORDER BY favourites.date DESC")
    suspend fun getFavourites(): List<FavouriteEntity>

    @Query("SELECT * FROM favourites WHERE title LIKE '%' || :titleQuery || '%' ORDER BY favourites.date DESC")
    suspend fun getFavouritesByTitle(titleQuery: String): List<FavouriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavourite(favourite: FavouriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavouriteEntity)
}