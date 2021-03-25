package com.morris.nasaimages.presentation.apod

import androidx.lifecycle.*
import com.morris.nasaimages.application.AppConstants.API_KEY
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.apod.Apod
import com.morris.nasaimages.domain.apod.ApodRepository
import com.morris.nasaimages.utils.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ApodViewModel(private val repository: ApodRepository) : ViewModel() {

    private val _apodData = MutableLiveData<List<Apod>>().apply { value = listOf() }
    val apodData: LiveData<List<Apod>> = _apodData

    private val _isLoadig = MutableLiveData<Boolean>()
    val isLoadig: LiveData<Boolean> = _isLoadig

    private val _onMessageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError


    init {
        _isLoadig.value = true

        loadApod()
    }

    private fun loadApod() {

        viewModelScope.launch {

            repository.getApod(Utils.getStartDate(), Utils.getEndDate(), API_KEY).collect {

                when (it) {
                    is Resource.Loading -> {
                        _isLoadig.value = true
                    }
                    is Resource.Failure -> {

                        _isLoadig.value = false
                        _onMessageError.value = it.exception.message.toString()
                    }
                    is Resource.Success -> {

                        if (it.data.isNotEmpty()) _isLoadig.value = false
                        _apodData.value = it.data
                    }
                }
            }
        }
    }
}