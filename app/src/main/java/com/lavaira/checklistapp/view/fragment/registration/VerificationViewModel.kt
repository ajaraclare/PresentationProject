package com.lavaira.checklistapp.view.fragment.registration

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.architecture.SingleLiveEvent
import com.lavaira.checklistapp.common.AppSession
import com.lavaira.checklistapp.data.remote.api.Response
import com.lavaira.checklistapp.data.remote.api.ResponseListener
import com.lavaira.checklistapp.data.remote.api.ResponseStatus
import com.lavaira.checklistapp.data.remote.model.response.registration.Verification
import com.lavaira.checklistapp.repository.AuthRepository
import com.lavaira.checklistapp.utils.SafeLet
import com.lavaira.checklistapp.viewmodel.BaseViewModel
import javax.inject.Inject

/****
 * Verficiation viewmodel
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
class VerificationViewModel @Inject constructor(private val authRepository: AuthRepository)
    :BaseViewModel(), SafeLet {

    val otp = MutableLiveData<String>()
    val otpFieldErrorValue = MutableLiveData<Int>()
    val verficiationSuccessEvent = SingleLiveEvent<Boolean>()
    val resendOtpEvent = SingleLiveEvent<Boolean>()


    fun validateOtp() {
        if (validateOtpField()) {
            safeLet(AppSession.verificationCode, otp.value) { verificationCode, otp ->
                authRepository.validateOtp(
                    verificationCode,
                    otp,
                    object :
                        ResponseListener<FirebaseUser> {
                        override fun onStart() {
                            loadingStatus.value = true
                        }

                        override fun onFinish() {
                            loadingStatus.value = false
                        }

                        override fun onResponse(result: Response<FirebaseUser>) {
                            loadingStatus.value = false
                            if (result.status == ResponseStatus.SUCCESS) {
                                AppSession.user = result.data
                                getIdentityToken()
                            } else {
                                serviceErrorEvent.value = result.error?.message
                            }
                        }

                    })
            }


        }
    }


    fun resendOtp() {

        safeLet(AppSession.phoneNumber, AppSession.resendToken) { phoneNumber, resendToken ->
            authRepository.resendVerificationCode(
                phoneNumber,
                resendToken,
                object :
                    ResponseListener<Verification> {
                    override fun onStart() {
                        loadingStatus.value = true
                    }

                    override fun onFinish() {
                        loadingStatus.value = false
                    }

                    override fun onResponse(result: Response<Verification>) {
                        loadingStatus.postValue(false)
                        if (result.status == ResponseStatus.SUCCESS) {
                            AppSession.verificationCode = result.data?.verificationCode
                            AppSession.resendToken = result.data?.resendToken
                            resendOtpEvent.postValue(true)
                        } else
                            serviceErrorEvent.postValue(result.error?.message)

                    }

                })
        }

    }

    private fun validateOtpField() : Boolean{
        return if (otp.value.isNullOrEmpty() || otp.value?.length!! < 6) {
            otpFieldErrorValue.value = R.string.error_msg_invalid_otp
            false
        } else {
            otpFieldErrorValue.value = R.string.error_msg_empty
            true
        }
    }


    private fun getIdentityToken(){
        authRepository.getIdentityToken(object:
            ResponseListener<String> {
            override fun onStart() {
                loadingStatus.value = true
            }

            override fun onFinish() {
                loadingStatus.value = false
            }

            override fun onResponse(result: Response<String>) {
                loadingStatus.value = false
                if(result.status == ResponseStatus.SUCCESS) {
                    AppSession.idToken = result.data
                    verficiationSuccessEvent.call()
                }else
                    serviceErrorEvent.value = result.error?.message
            }

        })
    }


}