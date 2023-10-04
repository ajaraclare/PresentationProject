package com.lavaira.checklistapp.data.remote.api

import com.google.gson.annotations.SerializedName

/****
 * Error Model
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
data class Error(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("message")
    var message: String = ""
)