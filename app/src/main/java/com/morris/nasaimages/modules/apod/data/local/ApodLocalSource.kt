package com.morris.nasaimages.modules.apod.data.local

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.application.AppDatabase
import com.morris.nasaimages.modules.apod.data.model.Apod
import com.morris.nasaimages.modules.apod.data.model.ApodEntity
import com.morris.nasaimages.modules.apod.data.model.asApodList

class ApodLocalSource(private val appDatabase: AppDatabase) {

    suspend fun getApod(): Resource<List<Apod>> {

        return Resource.Success(appDatabase.ApodDao().getApod().asApodList())
    }

    suspend fun saveApod(apod: ApodEntity) {

        appDatabase.ApodDao().saveApod(apod)
    }

    suspend fun clearTable() {

        appDatabase.ApodDao().clearTable()
    }
}