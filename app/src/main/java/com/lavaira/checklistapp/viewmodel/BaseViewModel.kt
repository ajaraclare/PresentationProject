package com.lavaira.checklistapp.viewmodel

import android.content.SharedPreferences
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import com.lavaira.checklistapp.ChecklistApplication
import com.lavaira.checklistapp.architecture.SingleLiveEvent
import com.lavaira.checklistapp.utils.PreferenceUtil


/****
 * Base view model. All the common implementation of viewmodel goes here
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
abstract class BaseViewModel : ViewModel(), Observable {

    val loadingStatus: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val backPressAction: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val serviceErrorEvent: SingleLiveEvent<String> = SingleLiveEvent()

    private val callbacks = PropertyChangeRegistry()

    lateinit var sharedViewModel: SharedViewModel

    private var sharedPreferences: SharedPreferences =
        PreferenceUtil.customPrefs(ChecklistApplication.applicationContext(), "checklist_pref_file")

    open fun onBackpress() {
        backPressAction.value = true
    }


    override fun addOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    internal fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    internal fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    override fun onCleared() {
        super.onCleared()
    }


}