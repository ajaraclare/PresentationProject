package com.lavaira.checklistapp.data.remote.api


/**
 * A generic class that holds a value with its loading status.
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
data class Resource<out T>(val status: Status, val data: T?, val error: Throwable?, val errorCode: String, val errorMessage: String) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, "", "")
        }

        fun <T> error(error: Throwable, data: T?, errorCode: String, errorMessage: String): Resource<T> {
            return Resource(Status.ERROR, data, error, errorCode, errorMessage)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null, "", "")
        }
    }
}