package com.morris.nasaimages.modules.apod

import com.google.gson.GsonBuilder
import com.morris.nasaimages.application.AppConstants.APOD_URL
import com.morris.nasaimages.modules.apod.data.remote.ApodWebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApodRetrofitClient {

    companion object {

        private var instanceApod: Retrofit? = null

        fun retrofitInstance(): Retrofit {

            instanceApod = instanceApod ?: Retrofit.Builder()
                .baseUrl(APOD_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

            return instanceApod!!
        }

        fun apodWebService(retrofit: Retrofit): ApodWebService = retrofit.create(ApodWebService::class.java)
    }
}