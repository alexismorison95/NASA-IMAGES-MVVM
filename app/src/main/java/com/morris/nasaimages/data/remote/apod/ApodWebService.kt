package com.morris.nasaimages.data.remote.apod

import com.morris.nasaimages.data.model.apod.Apod
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodWebService {

    @GET("apod")
    suspend fun getApod(
        @Query(value = "start_date") startDate: String,
        @Query(value = "end_date") endDate: String,
        @Query(value = "api_key") apiKey: String,
    ): List<Apod>?
}