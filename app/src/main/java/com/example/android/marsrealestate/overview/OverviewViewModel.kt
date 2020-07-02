/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

enum class MarsApiStatus {LOADING, ERROR, DONE}

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<MarsApiStatus>()
    // The external immutable LiveData for the response String
    val status: LiveData<MarsApiStatus>
        get() = _status


    private val _properties  = MutableLiveData<List<MarsProperty>>()
    val properties : LiveData<List<MarsProperty>>
        get() = _properties

    // new coroutine Job
    private var viewModelJob = Job()
    // coroutine scope for that new job using the main dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {
        coroutineScope.launch {
            // Get the Deffered object for Retrofit request
            var getPropertiesDeffered = MarsApi.retrofitService.getPropertiesAsync()
            try {
                _status.value = MarsApiStatus.LOADING
                // Await completion of Retrofit request
                var listResult = getPropertiesDeffered.await()
                _status.value = MarsApiStatus.DONE
                if (listResult.isNotEmpty()) {
                    _properties.value = listResult
                }
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }
        //  stop loading when the ViewModel is destroyed - Cancel the JOb
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
