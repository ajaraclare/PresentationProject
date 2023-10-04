package com.lavaira.checklistapp.data.remote.api

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.annotations.NonNull

/****
 * A generic class to send loading event up-stream when fetching data
 * only from network.
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/

abstract class NetworkResource<ResultType> @MainThread constructor() {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {

        val apiResponse = loadFromNetwork()

        result.addSource(apiResponse) {response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    result.setValue(Resource.success(processResponse(response)))
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.setValue(Resource.error(response.error, null, response.errorCode, response.errorDescription))
                }

                is ApiEmptyResponse -> {
                    result.setValue(Resource.success(null))
                }
            }
        }
    }

    @NonNull
    @MainThread
    protected abstract fun loadFromNetwork(): LiveData<ApiResponse<ResultType>>

    protected fun onFetchFailed() {
        // Nothing to implement
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<ResultType>) = response.body}