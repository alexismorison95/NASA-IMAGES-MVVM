package com.morris.nasaimages.data.remote.apod

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.apod.Apod
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.Flow

@ExperimentalCoroutinesApi
class ApodRemoteSource(private val webService: ApodWebService) {

    suspend fun getApod(startDate: String, endDate: String, apiKey: String) =

        callbackFlow {
            offer(
                Resource.Success(webService.getApod(startDate, endDate, apiKey).reversed())
            )
            awaitClose { close() }
        }

        // Descarto los videos
        /*val listFiltered = webService.getApod(startDate, endDate, apiKey).reversed().filter {
            it.media_type == "image"
        }

        return Resource.Success(listFiltered)*/

}