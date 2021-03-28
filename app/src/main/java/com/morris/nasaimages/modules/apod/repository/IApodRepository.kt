package com.morris.nasaimages.modules.apod.repository

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.modules.apod.data.model.Apod
import kotlinx.coroutines.flow.Flow

interface IApodRepository {

    suspend fun getApod(startDate: String, endDate: String, apiKey: String): Flow<Resource<List<Apod>>>
}