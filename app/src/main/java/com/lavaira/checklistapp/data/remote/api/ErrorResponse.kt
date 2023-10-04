package com.lavaira.checklistapp.data.remote.api

import com.google.gson.annotations.SerializedName

/****
 * Data Model class which represents the error response
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
data class ErrorResponse (@SerializedName("error")
                           var error: String? = "")