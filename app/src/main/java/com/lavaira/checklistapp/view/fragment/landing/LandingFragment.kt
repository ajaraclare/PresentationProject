package com.lavaira.checklistapp.view.fragment.landing

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.lavaira.checklistapp.BR
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.contract.SubscriptionContract
import com.lavaira.checklistapp.databinding.FragmentLandingBinding
import com.lavaira.checklistapp.utils.FragmentUtils
import com.lavaira.checklistapp.utils.Utils
import com.lavaira.checklistapp.view.fragment.base.BaseFragment
import com.lavaira.checklistapp.view.fragment.registration.VerifyOtpFragment


/****
 * Landing Screen
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
class LandingFragment : BaseFragment<LandingViewModel, FragmentLandingBinding>(), SubscriptionContract {

    override val layoutRes: Int
        get() = R.layout.fragment_landing
    override val bindingVariable: Int
        get() = BR.viewModel

    override val subscriptionContract: SubscriptionContract?
        get() = this

    override fun getViewModel(): Class<LandingViewModel> {
        return LandingViewModel::class.java
    }

    override fun getTitle(): String {
       return getString(R.string.app_name)
    }

    override fun subscribeNavigationEvent() {
        super.subscribeNavigationEvent()
        viewModel.registrationEvent.observe(this, Observer{
            Utils.hideKeyboard(activity)
            FragmentUtils.replaceFragment(activity as AppCompatActivity, VerifyOtpFragment(), R.id.fragmentContainer,
                true, FragmentUtils.FragmentAnimation.TRANSITION_SLIDE_LEFT_RIGHT)

        })
    }
}