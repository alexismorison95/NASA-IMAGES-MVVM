package com.morris.nasaimages.domain.main

import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.main.MainItem

interface IMainRepository {

    fun getMainItems(): Resource<List<MainItem>>
}