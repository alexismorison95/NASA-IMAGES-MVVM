package com.morris.nasaimages.modules.library

import com.google.gson.GsonBuilder
import com.morris.nasaimages.application.AppConstants.LIBRARY_URL
import com.morris.nasaimages.modules.library.data.remote.LibraryWebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibraryRetrofitClient {

    companion object {

        private var instance: Retrofit? = null

        fun retrofitInstance(): Retrofit {

            instance = instance ?: Retrofit.Builder()
                .baseUrl(LIBRARY_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

            return instance!!
        }

        fun libraryWebService(retrofit: Retrofit): LibraryWebService = retrofit.create(LibraryWebService::class.java)
    }
}