package com.lavaira.checklistapp.view.fragment.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.contract.SubscriptionContract
import com.lavaira.checklistapp.di.Injectable
import com.lavaira.checklistapp.viewmodel.BaseViewModel
import com.lavaira.checklistapp.viewmodel.SharedViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/****
 * BaseBottom sheet dialog fragment
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-16
 * Modified on: 2020-03-16
 *****/
abstract class BaseBottomSheetDialogFragment<V : ViewModel, D : ViewDataBinding> : BottomSheetDialogFragment(),
    Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: V

    protected lateinit var dataBinding: D

    protected lateinit var sharedViewModel: SharedViewModel

    @get:LayoutRes
    protected abstract val layoutRes: Int

    abstract val bindingVariable: Int

    protected abstract fun getViewModel(): Class<V>

    open val subscriptionContract: SubscriptionContract? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        activity?.let { activity ->
            sharedViewModel = ViewModelProvider(activity).get(SharedViewModel::class.java)
        }
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModel())
        (viewModel as BaseViewModel).sharedViewModel = sharedViewModel

        (viewModel as BaseViewModel).serviceErrorEvent.observe(this, Observer {
            showErrorDialog(it)
        })



    }

    /**
     * Method which sets the sharedview to baseviewmodel
     */
    private fun setSharedViewModel() {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.setVariable(bindingVariable, viewModel)
        dataBinding.executePendingBindings()
        subscriptionContract?.subscribeNavigationEvent()
        subscriptionContract?.subscribeNetworkResponse()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptionContract?.unsubscribe()
    }

    private fun blockUserInteraction(isLoading: Boolean) {
        if (isLoading) {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isTransparent()) {
            view?.parent?.apply {
                (this as View).setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    open fun isTransparent(): Boolean = false



    override fun onDestroyView() {
        super.onDestroyView()
        (viewModel as? BaseViewModel)?.loadingStatus?.removeObservers(this)
    }

    fun showErrorDialog(message : String){

        val act : FragmentActivity =  activity?.let { it } ?: return
        val builder = AlertDialog.Builder(act)
        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton(getString(R.string.ok)){ dialog, which ->
            dialog.dismiss()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

}