package com.lavaira.checklistapp.data.remote.api

import androidx.lifecycle.LiveData
import com.google.gson.JsonObject
import com.lavaira.checklistapp.data.remote.model.response.registration.RegistrationResponse
import com.lavaira.checklistapp.data.remote.model.response.tasks.Task
import retrofit2.http.*


/****
 * Keep all the REST Apis here
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
interface Api {

    @PUT("Users/{userid}")
    fun register(@Path("userid")userId: String,
                 @Body map: HashMap<String, String>) : LiveData<ApiResponse<RegistrationResponse>>

    @PUT("Users/{userid}/Tasks/{taskId}")
    fun addOrUpdateTask(@Path("userid")userId: String, @Path("taskId") taskId: String, @Body map: HashMap<String, String>) : LiveData<ApiResponse<Task>>


    @DELETE("Users/{userid}/Tasks/{taskId}")
    fun deleteTask(@Path("userid")userId: String, @Path("taskId")taskId: String) : LiveData<ApiResponse<Void>>


    @GET("Users/{userid}/Tasks.json")
    fun getTasks(@Path("userid")userId: String) : LiveData<ApiResponse<JsonObject>>

}