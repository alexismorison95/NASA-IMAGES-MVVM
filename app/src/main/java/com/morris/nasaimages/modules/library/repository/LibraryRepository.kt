package com.morris.nasaimages.modules.library.repository

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.modules.library.data.model.Library
import com.morris.nasaimages.modules.library.data.remote.LibraryRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class LibraryRepository(private val remoteSource: LibraryRemoteSource) : ILibraryRepository {

    override suspend fun getLibrary(query: String, page: String, startYear: String, endYear: String): Flow<Resource<Library>> =

        callbackFlow {

            remoteSource.getLibrary(query, page, startYear, endYear).collect {

                offer(it)
            }
            awaitClose()
        }
}