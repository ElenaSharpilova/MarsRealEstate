package com.example.android.marsrealestate.overview


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

    private val _property = MutableLiveData<MarsProperty>()

    val property: LiveData<MarsProperty>
        get() = _property

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            try {
                var listResult = MarsApi.retrofitService.getProperties()
                _status.value = "Success: ${listResult.size} Mars properties retrieved"
                if (listResult.size > 0) {
                    _property.value = listResult[0]
                }
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}