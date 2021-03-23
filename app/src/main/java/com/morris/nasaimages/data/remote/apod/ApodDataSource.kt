package com.morris.nasaimages.data.remote.apod

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.apod.Apod

class ApodDataSource(private val webService: ApodWebService) {

    suspend fun getApod(startDate: String, endDate: String, apiKey: String): Resource<List<Apod>> {

        // Descarto los videos
        val listFiltered = webService.getApod(startDate, endDate, apiKey).reversed().filter {
            it.media_type == "image"
        }

        return Resource.Success(listFiltered)
    }
}