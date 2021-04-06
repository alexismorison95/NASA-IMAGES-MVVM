package com.morris.nasaimages.modules.library.data.remote

import android.util.Log
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.modules.library.data.model.Asset
import com.morris.nasaimages.modules.library.data.model.Library
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception

@ExperimentalCoroutinesApi
class LibraryRemoteSource(private val webService: LibraryWebService) {

    suspend fun getLibrary(query: String, page: String, startYear: String, endYear: String): Flow<Resource<Library>> =

        callbackFlow {
            try {
                offer(
                    Resource.Success(webService.search(query, page, startYear, endYear))
                )
            }
            catch (e: Exception) {
                offer(Resource.Failure(e))

                Log.d("TAG", "getLibrary: $e")
            }
            awaitClose()
        }

    suspend fun getAsset(id: String): Flow<Resource<Asset>> =

        callbackFlow {
            try {
                offer(
                    Resource.Success(webService.getAsset(id))
                )
            }
            catch (e: Exception) {
                offer(Resource.Failure(e))
            }
            awaitClose()
        }
}