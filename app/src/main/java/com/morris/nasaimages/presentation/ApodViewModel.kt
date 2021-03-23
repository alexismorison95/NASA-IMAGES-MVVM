package com.morris.nasaimages.presentation

import android.util.Log
import androidx.lifecycle.*
import com.morris.nasaimages.application.AppConstants.API_KEY
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.apod.Apod
import com.morris.nasaimages.domain.apod.ApodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApodViewModel(private val repository: ApodRepository) : ViewModel() {

    private val apodData: MutableLiveData<List<Apod>> by lazy {
        MutableLiveData<List<Apod>>()
    }

    private val current = LocalDateTime.now()
    private val last = current.minusDays(15)

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val endDate = current.format(formatter)
    private val startDate = last.format(formatter)


    fun setApodData(list: List<Apod>) {

        apodData.value = list
    }

    fun getApodData(): List<Apod>? = apodData.value


    fun loadApod() = liveData<Resource<List<Apod>>>(Dispatchers.IO) {

        emit(Resource.Loading())

        try {
            emit(repository.getApod(startDate, endDate, API_KEY))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}