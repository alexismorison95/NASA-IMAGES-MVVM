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

    private val _apodData = MutableLiveData<List<Apod>>().apply { value = listOf() }
    val apodData: LiveData<List<Apod>> = _apodData

    private val _isLoadig = MutableLiveData<Boolean>()
    val isLoadig: LiveData<Boolean> = _isLoadig

    private val _onMessageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError


    private val current = LocalDateTime.now()
    private val last = current.minusDays(15)

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val endDate = current.format(formatter)
    private val startDate = last.format(formatter)


    init {
        _isLoadig.value = true

        loadApod()
    }

    private fun loadApod() {

        viewModelScope.launch {

            repository.getApod(startDate, endDate, API_KEY).collect {

                when (it) {
                    is Resource.Loading -> {
                        _isLoadig.value = true
                    }
                    is Resource.Failure -> {

                        _isLoadig.value = false
                        _onMessageError.value = it.exception.message.toString()
                    }
                    is Resource.Success -> {

                        Log.d("TAG", "${it.data}")

                        if (it.data.isNotEmpty()) _isLoadig.value = false
                        _apodData.value = it.data
                    }
                }
            }
        }

    }
}