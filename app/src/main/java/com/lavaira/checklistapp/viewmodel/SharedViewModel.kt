package com.lavaira.checklistapp.viewmodel

import androidx.lifecycle.ViewModel
import com.lavaira.checklistapp.data.remote.model.response.tasks.Task

/****
 * Shared viewmodel for data sharing
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 2020-03-16
 * Modified on: 2020-03-16
 *****/
open class SharedViewModel : ViewModel() {

    var selectedTask: Task? = null
    var isEditMode: Boolean = false
}