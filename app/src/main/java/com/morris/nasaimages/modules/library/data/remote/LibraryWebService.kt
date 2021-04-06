package com.morris.nasaimages.modules.library.data.remote

import com.morris.nasaimages.modules.library.data.model.Asset
import com.morris.nasaimages.modules.library.data.model.Library
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LibraryWebService {

    @GET("search")
    suspend fun search(
        @Query(value = "q") query: String,
        @Query(value = "page") page: String,
        @Query(value = "year_start") startYear: String,
        @Query(value = "year_end") endYear: String,
        @Query(value = "media_type") type: String = "image",
    ): Library

    @GET("asset/{id}")
    suspend fun getAsset(
        @Path(value = "id") id: String
    ): Asset
}