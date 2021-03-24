package com.morris.nasaimages.domain.apod

import android.util.Log
import androidx.lifecycle.LiveData
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.local.apod.ApodLocalSource
import com.morris.nasaimages.data.model.apod.Apod
import com.morris.nasaimages.data.model.apod.asApodEntity
import com.morris.nasaimages.data.remote.apod.ApodRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class ApodRepository(
    private val remoteSource: ApodRemoteSource,
    private val localSource: ApodLocalSource
    ) : IApodRepository {

    override suspend fun getApod(startDate: String, endDate: String, apiKey: String): Flow<Resource<List<Apod>>> =

        callbackFlow {

            offer(localSource.getApod())

            remoteSource.getApod(startDate, endDate, apiKey).collect {

                when (it) {

                    is Resource.Success -> {

                        localSource.clearTable()

                        for (apod in it.data) {
                            if (apod.media_type == "image") localSource.saveApod(apod.asApodEntity())
                        }

                        offer(localSource.getApod())
                    }
                    is Resource.Failure -> {

                        offer(it)
                        offer(localSource.getApod())
                    }
                   is Resource.Loading -> {}
                }
            }
            awaitClose { close() }
        }
}