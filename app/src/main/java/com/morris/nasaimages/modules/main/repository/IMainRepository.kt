package com.morris.nasaimages.modules.main.repository

import com.morris.nasaimages.modules.main.data.model.MainItem

interface IMainRepository {

    fun getMainItems(): List<MainItem>
}