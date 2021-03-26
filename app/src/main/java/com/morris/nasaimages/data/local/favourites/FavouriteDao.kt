package com.morris.nasaimages.data.local.favourites

import androidx.lifecycle.LiveData
import androidx.room.*
import com.morris.nasaimages.data.model.favourites.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    suspend fun getFavourites(): List<FavouriteEntity>

   /* @Query("SELECT * FROM favourites")
    fun getFavouritesLiveData(): LiveData<List<FavouriteEntity>>*/

    @Query("SELECT * FROM favourites WHERE title LIKE '%' || :titleQuery || '%'")
    suspend fun getFavouritesByTitle(titleQuery: String): List<FavouriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavourite(favourite: FavouriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavouriteEntity)
}