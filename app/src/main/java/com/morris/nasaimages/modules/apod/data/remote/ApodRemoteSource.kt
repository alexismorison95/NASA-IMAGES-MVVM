package com.morris.nasaimages.modules.apod.data.remote

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.modules.apod.data.model.Apod
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class ApodRemoteSource(private val webService: ApodWebService) {

    suspend fun getApod(startDate: String, endDate: String, apiKey: String): Flow<Resource<List<Apod>>> =

        callbackFlow {
            try {
                offer(
                    Resource.Success(webService.getApod(startDate, endDate, apiKey)?.reversed() ?: listOf())
                )
            }
            catch (e: Exception) {
                offer(Resource.Failure(e))
            }
            awaitClose { close() }
        }
}