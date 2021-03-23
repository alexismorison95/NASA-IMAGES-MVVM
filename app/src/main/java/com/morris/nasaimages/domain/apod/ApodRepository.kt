package com.morris.nasaimages.domain.apod

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.apod.Apod
import com.morris.nasaimages.data.remote.apod.ApodDataSource

class ApodRepository(private val dataSource: ApodDataSource) : IApodRepository {

    override suspend fun getApod(startDate: String, endDate: String, apiKey: String): Resource<List<Apod>> {

        return dataSource.getApod(startDate, endDate, apiKey)
    }
}