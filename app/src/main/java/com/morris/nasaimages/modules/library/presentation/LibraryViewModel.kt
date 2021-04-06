package com.morris.nasaimages.modules.library.presentation

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.modules.library.data.model.Asset
import com.morris.nasaimages.modules.library.data.model.Library
import com.morris.nasaimages.modules.library.repository.ILibraryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class LibraryViewModel(private val repository: ILibraryRepository) : ViewModel() {

    private val _libraryData = MutableLiveData<Library?>()
    val libraryData: LiveData<Library?> = _libraryData

    private val _isLoadig = MutableLiveData<Boolean>()
    val isLoadig: LiveData<Boolean> = _isLoadig

    private val _onMessageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError


    fun loadLibrary(query: String, page: String, startYear: String, endYear: String) {

        viewModelScope.launch {

            repository.getLibrary(query, page, startYear, endYear).collect {

                when (it) {

                    is Resource.Loading -> {
                        _isLoadig.value = true
                    }
                    is Resource.Failure -> {

                        _isLoadig.value = false
                        _onMessageError.value = it.exception.message.toString()
                    }
                    is Resource.Success -> {

                        _isLoadig.value = false
                        _libraryData.value = it.data
                    }
                }
            }
        }
    }

    fun loadAsset(id: String) =

        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {

            repository.getAsset(id).collect {

                when (it) {

                    is Resource.Loading -> { }
                    is Resource.Failure -> { }
                    is Resource.Success -> { emit(it.data) }
                }
            }
    }
}