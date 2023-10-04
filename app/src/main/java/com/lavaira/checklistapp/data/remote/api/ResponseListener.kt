package com.lavaira.checklistapp.data.remote.api

/****
 * ResponseListener callback which is responsible for giving the API response back to the presentation layer
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
interface ResponseListener<T> {
    fun onStart()
    fun onFinish()
    fun onResponse(result: Response<T>)
}