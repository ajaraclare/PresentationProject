package com.lavaira.checklistapp.view.fragment.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lavaira.checklistapp.architecture.AbsentLiveData
import com.lavaira.checklistapp.architecture.SingleLiveEvent
import com.lavaira.checklistapp.common.AppSession
import com.lavaira.checklistapp.common.Constants
import com.lavaira.checklistapp.data.remote.api.Resource
import com.lavaira.checklistapp.data.remote.model.response.tasks.Task
import com.lavaira.checklistapp.repository.UserRepository
import com.lavaira.checklistapp.viewmodel.BaseViewModel
import java.util.*
import javax.inject.Inject

/****
 * AddTaskViewModel
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-16
 * Modified on: 2020-03-16
 *****/
class AddTaskViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    private val addTaskRequest = MutableLiveData<Task>()
    private val deleteTaskRequest = MutableLiveData<Boolean>()
    val validationErrorEvent = SingleLiveEvent<Void>()

    val taskTitle = MutableLiveData<String>("")
    val taskDesc = MutableLiveData<String>("")
    val startDate = MutableLiveData<String>("")
    val endDate = MutableLiveData<String>("")
    val status = MutableLiveData<String>("")
    private val nodeId = MutableLiveData<String>("")
    val isInEditMode = MutableLiveData<Boolean>(false)


    init {
        status.value = Constants.TASK_STATUS.TASK_TODO
    }


    fun initData(){
        if(sharedViewModel.isEditMode){
            isInEditMode.value = sharedViewModel.isEditMode
            taskTitle.value = sharedViewModel.selectedTask?.title
            taskDesc.value = sharedViewModel.selectedTask?.description
            startDate.value = sharedViewModel.selectedTask?.startDate
            endDate.value = sharedViewModel.selectedTask?.endDate
            status.value = sharedViewModel.selectedTask?.status
            nodeId.value = sharedViewModel.selectedTask?.nodeId
        }else{
            nodeId.value = UUID.randomUUID().toString()
        }
    }


    val addTaskResponse: LiveData<Resource<Task>> = Transformations
        .switchMap(addTaskRequest){ request->
            if(null == request)
                AbsentLiveData.create()
            else{
                userRepository.addOrUpdateTask(request, sharedViewModel.isEditMode)
            }
        }

    val deleteTaskResponse: LiveData<Resource<Void>> = Transformations
        .switchMap(deleteTaskRequest){request ->
            if(null == request)
                AbsentLiveData.create()
            else{
                userRepository.deleteTask(AppSession.user?.uid, nodeId.value.toString())
            }
        }

    fun addTask(){
        if(validateFields())
            addTaskRequest.value = Task(title = taskTitle.value.toString(), description = taskDesc.value.toString(),
                startDate = startDate.value.toString(), endDate = endDate.value.toString(), status = status.value.toString(), nodeId = nodeId.value.toString())
        else{
            validationErrorEvent.call()
        }
    }


    fun deleteTask(){
        deleteTaskRequest.value = true
    }


    fun changeTaskStatus(){
        if(status.value == Constants.TASK_STATUS.TASK_TODO){
            status.value = Constants.TASK_STATUS.TASK_INPROGRESS
        }else if(status.value == Constants.TASK_STATUS.TASK_INPROGRESS)
            status.value = Constants.TASK_STATUS.TASK_COMPLETED
    }


    private fun validateFields() : Boolean{
        return (!taskTitle.value.isNullOrEmpty()
                && !startDate.value.isNullOrEmpty() && !endDate.value.isNullOrEmpty())
    }
}