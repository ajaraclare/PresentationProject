package com.lavaira.checklistapp.data.remote.model.response.registration

import com.google.firebase.auth.PhoneAuthProvider

/****
 * Model fro Verification code
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
data class Verification(
    val verificationCode: String? = "",
    val resendToken: PhoneAuthProvider.ForceResendingToken? = null
)