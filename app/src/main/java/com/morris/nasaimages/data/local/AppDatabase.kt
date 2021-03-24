package com.morris.nasaimages.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.morris.nasaimages.application.AppConstants.DATABASE_NAME
import com.morris.nasaimages.data.local.apod.ApodDao
import com.morris.nasaimages.data.local.favourites.FavouriteDao
import com.morris.nasaimages.data.model.apod.ApodEntity
import com.morris.nasaimages.data.model.favourites.FavouriteEntity

@Database(entities = [FavouriteEntity::class, ApodEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao
    abstract fun ApodDao(): ApodDao

    companion object {

        private var instance: AppDatabase? = null

        fun getRoomInstance(context: Context): AppDatabase {

            instance = instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

            return instance!!
        }
    }
}