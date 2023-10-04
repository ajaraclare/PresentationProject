package com.lavaira.checklistapp.view.fragment.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lavaira.checklistapp.ChecklistApplication
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.contract.SubscriptionContract
import com.lavaira.checklistapp.di.Injectable
import com.lavaira.checklistapp.utils.PreferenceUtil
import com.lavaira.checklistapp.view.activity.base.BaseActivity
import com.lavaira.checklistapp.view.listeners.BackButtonHandlerListener
import com.lavaira.checklistapp.view.listeners.BackPressListener
import com.lavaira.checklistapp.viewmodel.BaseViewModel
import com.lavaira.checklistapp.viewmodel.SharedViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/****
 * Parent for all the UI fragments. All the common things to be kept here
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
abstract class BaseFragment<V : ViewModel, D : ViewDataBinding> : androidx.fragment.app.Fragment(),
    Injectable, BackPressListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: V

    protected lateinit var dataBinding: D


    private var backButtonHandler: BackButtonHandlerListener? = null

    @get:LayoutRes
    protected abstract val layoutRes: Int

    abstract val bindingVariable: Int

    protected abstract fun getViewModel(): Class<V>

    abstract fun getTitle(): String

    open val subscriptionContract: SubscriptionContract? = null

    protected lateinit var sharedViewModel: SharedViewModel

    protected var sharedPreferences: SharedPreferences =
        PreferenceUtil.customPrefs(ChecklistApplication.applicationContext(), "checklist_pref_file")

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        activity?.let {
            sharedViewModel = ViewModelProvider(activity!!).get(SharedViewModel::class.java)

        }
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModel())
        (viewModel as BaseViewModel).serviceErrorEvent.observe(this, Observer {
            showErrorDialog(it)
        })
        setSharedViewModel()

    }


    /**
     * Method which sets the sharedview to baseviewmodel
     */
    private fun setSharedViewModel() {
        (viewModel as BaseViewModel).sharedViewModel = sharedViewModel
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTitle()
    }


    /**
     * Method which sets the title of the view
     */
    private fun setTitle() {
        activity?.let {
            when (it) {
                is BaseActivity<*, *> ->
                    it.setTitle(getTitle())
            }
        }
    }


    /**
     * Method which indicates if the fragment has header
     * Default (true): considering all fragment has header
     */
    open fun hasHeader(): Boolean {
        return true
    }

    /**
     * Method to override the backpress behaviour on indivitual fragment
     */
    override fun onBackPress(): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscriptionContract?.unsubscribe()
    }

    override fun onResume() {
        super.onResume()
        backButtonHandler?.addBackpressListener(this)
    }

    override fun onPause() {
        super.onPause()
        backButtonHandler?.removeBackpressListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonHandler = context as BackButtonHandlerListener
    }

    override fun onDetach() {
        super.onDetach()
        backButtonHandler?.removeBackpressListener(this)
        backButtonHandler = null
    }

    fun showErrorDialog(message : String){

        val act : FragmentActivity =  activity?.let { it } ?: return
        val builder = AlertDialog.Builder(act)
        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton(getString(R.string.ok)){dialog, which ->
            dialog.dismiss()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

}