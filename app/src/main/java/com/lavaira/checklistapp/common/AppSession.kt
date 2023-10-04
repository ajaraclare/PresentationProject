package com.lavaira.checklistapp.common

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider

/****
 * Application session class
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
object AppSession {
    var idToken: String? = null
    var localId: String? = ""
    var verificationCode: String? = ""
    var phoneNumber: String? = ""
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    var user: FirebaseUser? = null

}