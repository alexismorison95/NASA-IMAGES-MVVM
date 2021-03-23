package com.morris.nasaimages.domain.apod

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.apod.Apod

interface IApodRepository {

    suspend fun getApod(startDate: String, endDate: String, apiKey: String): Resource<List<Apod>>
}