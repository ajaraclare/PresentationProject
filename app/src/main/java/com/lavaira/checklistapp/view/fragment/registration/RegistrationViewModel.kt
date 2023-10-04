package com.lavaira.checklistapp.view.fragment.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.architecture.AbsentLiveData
import com.lavaira.checklistapp.data.remote.api.Resource
import com.lavaira.checklistapp.data.remote.model.request.register.RegistrationRequest
import com.lavaira.checklistapp.data.remote.model.response.registration.RegistrationResponse
import com.lavaira.checklistapp.repository.UserRepository
import com.lavaira.checklistapp.utils.SafeLet
import com.lavaira.checklistapp.utils.Validator
import com.lavaira.checklistapp.viewmodel.BaseViewModel
import javax.inject.Inject

/****
 * Registration viewmodel
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository
) :
    BaseViewModel(), SafeLet {

    val firstNameField = MutableLiveData<String>()
    val lastNameField = MutableLiveData<String>()
    val addressField = MutableLiveData<String>()
    val emailField = MutableLiveData<String>()


    val firstNameFieldErrorValue = MutableLiveData<Int>()
    val lastNameFieldErrorValue = MutableLiveData<Int>()
    val addressFieldErrorValue = MutableLiveData<Int>()
    val emailFieldErrorValue = MutableLiveData<Int>()

    val updateProfileRequest = MutableLiveData<RegistrationRequest>()



    val updateProfileResponse: LiveData<Resource<RegistrationResponse>> = Transformations
        .switchMap(updateProfileRequest){ request->
            if(null == request)
                AbsentLiveData.create()
            else{
                userRepository.saveUserProfile(request)
            }
        }



    private fun validateFirstName(): Boolean {
        return if (firstNameField.value.isNullOrEmpty()) {
            firstNameFieldErrorValue.value = R.string.error_msg_invalid_firstname
            false
        } else {
            firstNameFieldErrorValue.value = R.string.error_msg_empty
            true
        }
    }


    private fun validateLastName(): Boolean {
        return if (lastNameField.value.isNullOrEmpty()) {
            lastNameFieldErrorValue.value = R.string.error_msg_invalid_lastname
            false
        } else {
            lastNameFieldErrorValue.value = R.string.error_msg_empty
            true
        }
    }


    private fun validateAddress(): Boolean {
        return if (addressField.value.isNullOrEmpty()) {
            addressFieldErrorValue.value = R.string.error_msg_invalid_address
            false
        } else {
            addressFieldErrorValue.value = R.string.error_msg_empty
            true
        }
    }


    /**
     * validating Email
     */
    private fun validateEmail(): Boolean {

        return if (emailField.value.isNullOrEmpty() || !Validator.isValidEmail(emailField.value.toString())) {
            emailFieldErrorValue.value = R.string.error_msg_invalid_email
            false
        } else {
            emailFieldErrorValue.value = R.string.error_msg_empty
            true
        }
    }


    /**
     * All fields validation
     */
    private fun isAllFieldsValid(): Boolean {

        val isValidEmail = validateEmail()
        val isValidName = validateFirstName()
        val isValidLastName = validateLastName()
        val isValidAddress = validateAddress()
        if (isValidName && isValidEmail && isValidAddress && isValidLastName) {
            return true
        }
        return false
    }


    /**
     * Service call to perform the signup operation
     */
    fun saveUserProfile() {
        if(isAllFieldsValid()) {

            updateProfileRequest.value = RegistrationRequest(
                firstNameField.value.toString(),
                lastNameField.value.toString(),
                addressField.value.toString(),
                emailField.value.toString()
            )
        }

    }



}