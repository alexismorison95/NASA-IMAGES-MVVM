package com.morris.nasaimages.data.local.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.local.AppDatabase
import com.morris.nasaimages.data.model.apod.Apod
import com.morris.nasaimages.data.model.apod.ApodEntity
import com.morris.nasaimages.data.model.apod.asApodList

class ApodLocalSource(private val appDatabase: AppDatabase) {

    fun getApod(): Resource<List<Apod>> {

        //return appDatabase.ApodDao().getApod().map { it.asApodList() }
        return Resource.Success(appDatabase.ApodDao().getApod().asApodList())
    }

    suspend fun saveApod(apod: ApodEntity) {

        appDatabase.ApodDao().saveApod(apod)
    }

    suspend fun clearTable() {

        appDatabase.ApodDao().clearTable()
    }
}