package com.morris.nasaimages.modules.library.repository

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.modules.library.data.model.Asset
import com.morris.nasaimages.modules.library.data.model.Library
import kotlinx.coroutines.flow.Flow

interface ILibraryRepository {

    suspend fun getLibrary(query: String, page: String, startYear: String, endYear: String): Flow<Resource<Library>>

    suspend fun getAsset(id: String): Flow<Resource<Asset>>
}