package com.morris.nasaimages.domain.apod

import androidx.lifecycle.LiveData
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.apod.Apod
import kotlinx.coroutines.flow.Flow

interface IApodRepository {

    suspend fun getApod(startDate: String, endDate: String, apiKey: String): Flow<Resource<List<Apod>>>
}