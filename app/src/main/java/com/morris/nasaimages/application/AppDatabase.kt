package com.morris.nasaimages.application

import android.content.Context
import androidx.room.*
import com.morris.nasaimages.application.AppConstants.DATABASE_NAME
import com.morris.nasaimages.modules.apod.data.local.ApodDao
import com.morris.nasaimages.modules.favourites.data.local.FavouriteDao
import com.morris.nasaimages.modules.apod.data.model.ApodEntity
import com.morris.nasaimages.modules.favourites.data.model.FavouriteEntity

@Database(entities = [FavouriteEntity::class, ApodEntity::class], version = 6)
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