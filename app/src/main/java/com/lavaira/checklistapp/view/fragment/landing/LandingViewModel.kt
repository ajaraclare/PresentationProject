package com.lavaira.checklistapp.view.fragment.landing

import androidx.lifecycle.MutableLiveData
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.architecture.SingleLiveEvent
import com.lavaira.checklistapp.common.AppSession
import com.lavaira.checklistapp.data.remote.api.Response
import com.lavaira.checklistapp.data.remote.api.ResponseListener
import com.lavaira.checklistapp.data.remote.api.ResponseStatus
import com.lavaira.checklistapp.data.remote.model.response.registration.Verification
import com.lavaira.checklistapp.repository.AuthRepository
import com.lavaira.checklistapp.viewmodel.BaseViewModel
import javax.inject.Inject

/****
 * Landing screen view model
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
class LandingViewModel @Inject constructor(private val authRepository: AuthRepository) : BaseViewModel() {

    val registrationEvent = SingleLiveEvent<Boolean>()

    val phoneNumberField = MutableLiveData<String>()
    val phoneNumberFieldError = MutableLiveData<Int>()


    fun doRegister(){
        if(validateMobileNumber()) {
            AppSession.phoneNumber = "+971"+phoneNumberField.value?.substring(1)
            authRepository.sendVerificationCode(AppSession.phoneNumber!!, object :
                ResponseListener<Verification> {
                override fun onStart() {
                    loadingStatus.value = true
                }

                override fun onFinish() {
                    loadingStatus.value = false
                }

                override fun onResponse(result: Response<Verification>) {
                    loadingStatus.postValue(false)
                    if(result.status == ResponseStatus.SUCCESS) {
                        AppSession.verificationCode = result.data?.verificationCode
                        AppSession.resendToken = result.data?.resendToken
                        registrationEvent.postValue(true)
                    }else
                        serviceErrorEvent.postValue(result.error?.message)

                }

            })

        }
    }


    private fun validateMobileNumber(): Boolean{
        return if (phoneNumberField.value.isNullOrEmpty() || phoneNumberField.value?.length!! < 10) {
            phoneNumberFieldError.value = R.string.error_msg_invalid_phone
            false
        } else {
            phoneNumberFieldError.value = R.string.error_msg_empty
            true
        }
    }
}