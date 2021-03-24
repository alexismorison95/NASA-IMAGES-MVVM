package com.morris.nasaimages.presentation.apod

import android.util.Log
import androidx.lifecycle.*
import com.morris.nasaimages.application.AppConstants.API_KEY
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.apod.Apod
import com.morris.nasaimages.domain.apod.ApodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExperimentalCoroutinesApi
class ApodViewModel(private val repository: ApodRepository) : ViewModel() {

    private var apodData: List<Apod> = mutableListOf()

    private val current = LocalDateTime.now()
    private val last = current.minusDays(15)

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val endDate = current.format(formatter)
    private val startDate = last.format(formatter)


    fun setApodData(list: List<Apod>) {

        apodData = list
    }

    fun getApodData(): List<Apod> = apodData


    /*fun loadApod() =
        liveData<Resource<List<Apod>>>(viewModelScope.coroutineContext + Dispatchers.IO) {

        emit(Resource.Loading())

        try {
            emitSource(repository.getApod(startDate, endDate, API_KEY).map { Resource.Success(it) })
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }*/

    fun loadApod() =
        liveData<Resource<List<Apod>>>(viewModelScope.coroutineContext + Dispatchers.IO) {

            emit(Resource.Loading())

            try {
                //emitSource(repository.getApod(startDate, endDate, API_KEY).map { Resource.Success(it) })
                repository.getApod(startDate, endDate, API_KEY).collect {
                    emit(it)
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}