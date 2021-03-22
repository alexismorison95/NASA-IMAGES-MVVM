package com.morris.nasaimages.domain.main

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.MainItem

interface IMainRepository {

    fun getMainItems(): Resource<List<MainItem>>
}