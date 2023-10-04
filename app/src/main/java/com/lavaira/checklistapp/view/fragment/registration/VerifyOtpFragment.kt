package com.lavaira.checklistapp.view.fragment.registration

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.lavaira.checklistapp.BR
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.contract.SubscriptionContract
import com.lavaira.checklistapp.databinding.FragmentVerifyOtpBinding
import com.lavaira.checklistapp.utils.FragmentUtils
import com.lavaira.checklistapp.utils.Utils
import com.lavaira.checklistapp.view.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_verify_otp.*

/****
 * Verification Fragment
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
class VerifyOtpFragment : BaseFragment<VerificationViewModel, FragmentVerifyOtpBinding>(), SubscriptionContract {

    override val layoutRes: Int
        get() = R.layout.fragment_verify_otp
    override val bindingVariable: Int
        get() = BR.viewModel

    override fun getViewModel(): Class<VerificationViewModel> {
        return VerificationViewModel::class.java
    }

    override val subscriptionContract: SubscriptionContract?
        get() = this

    override fun getTitle(): String {
       return getString(R.string.title_verification)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        otpView.setOtpCompletionListener { otp -> viewModel.otp.value = otp }
    }

    override fun subscribeNavigationEvent() {
        super.subscribeNavigationEvent()
        viewModel.verficiationSuccessEvent.observe(this, Observer{
            Utils.hideKeyboard(activity)
            FragmentUtils.replaceFragment(activity as AppCompatActivity, RegistrationFragment(), R.id.fragmentContainer,
                true, FragmentUtils.FragmentAnimation.TRANSITION_SLIDE_LEFT_RIGHT)

        })

        viewModel.resendOtpEvent.observe(this, Observer {
            otpView.text?.clear()
        })
    }
}