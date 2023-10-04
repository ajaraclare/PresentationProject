package com.lavaira.checklistapp.view.fragment.registration

import android.content.Intent
import androidx.lifecycle.Observer
import com.lavaira.checklistapp.BR
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.common.SharedPrefConstants
import com.lavaira.checklistapp.contract.SubscriptionContract
import com.lavaira.checklistapp.databinding.FragmentRegistrationBinding
import com.lavaira.checklistapp.utils.PreferenceUtil.set
import com.lavaira.checklistapp.utils.Utils
import com.lavaira.checklistapp.view.activity.DashboardActivity
import com.lavaira.checklistapp.view.fragment.base.BaseFragment

/****
 * Registration Fragment
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
class RegistrationFragment : BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>(), SubscriptionContract {
    override val layoutRes: Int
        get() = R.layout.fragment_registration
    override val bindingVariable: Int
        get() = BR.viewModel

    override val subscriptionContract: SubscriptionContract?
        get() = this

    override fun getViewModel(): Class<RegistrationViewModel> {
        return RegistrationViewModel::class.java
    }

    override fun getTitle(): String {
        return getString(R.string.title_Profile)
    }

    override fun subscribeNavigationEvent() {
        super.subscribeNavigationEvent()
        viewModel.updateProfileResponse.observe(this, Observer {

            when {
                it.status.isLoading() -> {
                    viewModel.loadingStatus.value = true
                }
                it.status.isSuccessful() -> {
                    viewModel.loadingStatus.value = false
                    Utils.hideKeyboard(activity)
                    sharedPreferences[SharedPrefConstants.SHARED_PREF_KEY_LOGGED_IN] = true
                    it.data?.let {
                        activity?.finish()
                        val dashboardIntent = Intent(activity, DashboardActivity::class.java)
                        startActivity(dashboardIntent)
                    }
                }
                it.status.isError() -> {
                    viewModel.loadingStatus.value = false
                    viewModel.serviceErrorEvent.value = it.errorMessage
                }
            }
        })
    }

}