package com.morris.nasaimages.modules.apod.data.local

import androidx.room.*
import com.morris.nasaimages.modules.apod.data.model.ApodEntity

@Dao
interface ApodDao {

    @Query("SELECT * FROM apod")
    suspend fun getApod(): List<ApodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveApod(apod: ApodEntity)

    @Query("DELETE FROM apod")
    suspend fun clearTable()
}