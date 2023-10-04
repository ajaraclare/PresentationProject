package com.lavaira.checklistapp.data.remote.model.request.register

import com.lavaira.checklistapp.common.Constants
import com.lavaira.checklistapp.data.remote.model.request.base.BaseRequest

/****
 * Registration Request Model
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
data class RegistrationRequest(
    val firstName: String,
    val lastName: String,
    val address: String,
    val email: String
) : BaseRequest() {

    override fun params(): HashMap<String, String> {
        val registrationParams = HashMap<String, String>()
        registrationParams[Constants.REGISTRATION_ATTRIBUTES.EMAIL] = email
        registrationParams[Constants.REGISTRATION_ATTRIBUTES.FIRST_NAME] = firstName
        registrationParams[Constants.REGISTRATION_ATTRIBUTES.LAST_NAME] = lastName
        registrationParams[Constants.REGISTRATION_ATTRIBUTES.ADDRESS] = address
        return registrationParams
    }
}