package com.morris.nasaimages.domain.main

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.local.main.MainDataSource
import com.morris.nasaimages.data.model.MainItem

class MainRepository(private val dataSource: MainDataSource) : IMainRepository {

    override fun getMainItems(): Resource<List<MainItem>> {

        return Resource.Success(dataSource.getMainItems())
    }
}