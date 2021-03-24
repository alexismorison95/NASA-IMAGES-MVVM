package com.morris.nasaimages.data.local.apod

import androidx.lifecycle.LiveData
import androidx.room.*
import com.morris.nasaimages.data.model.apod.ApodEntity

@Dao
interface ApodDao {

    @Query("SELECT * FROM apod")
    suspend fun getApod(): List<ApodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveApod(apod: ApodEntity)

    @Query("DELETE FROM apod")
    suspend fun clearTable()
}