package com.lavaira.checklistapp.data.remote.model.response.registration

import com.google.gson.annotations.SerializedName

/****
 * Registration Response
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/

data class RegistrationResponse(
    @SerializedName("email") val email: String? = "",
    @SerializedName("localId") val localId: String? = "",
    @SerializedName("idToken") val idToken: String? = "",
    @SerializedName("expiresIn") val expiresIn: String? = "",
    @SerializedName("refreshToken") val refreshToken: String? = ""
)