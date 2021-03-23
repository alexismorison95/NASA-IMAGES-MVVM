package com.morris.nasaimages.application

import com.google.gson.GsonBuilder
import com.morris.nasaimages.application.AppConstants.APOD_URL
import com.morris.nasaimages.data.remote.apod.ApodWebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {

        private var instanceApod: Retrofit? = null

        fun retrofitApodInstance(): Retrofit {

            instanceApod = instanceApod ?: Retrofit.Builder()
                .baseUrl(APOD_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

            return instanceApod!!
        }

        fun apodWebService(retrofit: Retrofit): ApodWebService {

            return retrofit.create(ApodWebService::class.java)
        }

    }
}