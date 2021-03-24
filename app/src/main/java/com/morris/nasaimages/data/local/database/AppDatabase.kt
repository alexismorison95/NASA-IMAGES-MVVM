package com.morris.nasaimages.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.morris.nasaimages.application.AppConstants.DATABASE_NAME
import com.morris.nasaimages.data.model.database.FavouriteEntity

@Database(entities = [FavouriteEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

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