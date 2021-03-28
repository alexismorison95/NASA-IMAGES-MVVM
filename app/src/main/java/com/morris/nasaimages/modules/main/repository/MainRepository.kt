package com.morris.nasaimages.modules.main.repository

import com.morris.nasaimages.modules.main.data.local.MainDataSource
import com.morris.nasaimages.modules.main.data.model.MainItem

class MainRepository(private val dataSource: MainDataSource) : IMainRepository {

    override fun getMainItems(): List<MainItem> {

        return dataSource.getMainItems()
    }
}