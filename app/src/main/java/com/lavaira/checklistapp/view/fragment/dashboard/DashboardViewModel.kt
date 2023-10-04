package com.lavaira.checklistapp.view.fragment.dashboard

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.lavaira.checklistapp.BR
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.architecture.AbsentLiveData
import com.lavaira.checklistapp.architecture.SingleLiveEvent
import com.lavaira.checklistapp.common.AppSession
import com.lavaira.checklistapp.data.remote.api.Resource
import com.lavaira.checklistapp.data.remote.model.response.tasks.Task
import com.lavaira.checklistapp.listeners.OnItemClickListener
import com.lavaira.checklistapp.repository.UserRepository
import com.lavaira.checklistapp.viewmodel.BaseViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding
import javax.inject.Inject

/****
 * Dashboard viewModel
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-16
 * Modified on: 2020-03-16
 *****/
class DashboardViewModel @Inject constructor(private val userRepository: UserRepository,
                                             private val databaseReference: DatabaseReference) : BaseViewModel(){


    val retreiveTaskRequest = MutableLiveData<String>()
    val addTaskEvent = SingleLiveEvent<Void>()
    val taskSelectedEvent = SingleLiveEvent<Void>()
    val items :ObservableList<Task> = ObservableArrayList()
    val itemBinding: ItemBinding<Task> =
        ItemBinding.of<Task>(BR.viewModel, R.layout.item_task)
            .bindExtra(BR.listener, object : OnItemClickListener{
                override fun onItemClick(item: Any) {
                    sharedViewModel.selectedTask = item as Task
                    sharedViewModel.isEditMode = true
                    taskSelectedEvent.call()
                }

            })


    init {
        AppSession.user = FirebaseAuth.getInstance().currentUser
    }

    val retrieveTasksResponse : LiveData<Resource<List<Task>>> = Transformations
        .switchMap(retreiveTaskRequest){ request->
            if(null == request)
                AbsentLiveData.create()
            else{
                userRepository.retrieveTasks(request)
            }
        }



    fun addTask(){
        sharedViewModel.selectedTask = null
        sharedViewModel.isEditMode = false
        addTaskEvent.call()
    }


    fun retrieveTasks() {
        retreiveTaskRequest.value = AppSession.user?.uid
    }



    fun syncDataWithFirebaseDB(tasks: ArrayList<Task>){
        databaseReference.child("Users")
            .child(AppSession.user?.uid.toString())
            .child("Tasks").removeValue()
        for (task in tasks){
            synchronized(this){
                databaseReference.child("Users")
                    .child(AppSession.user?.uid.toString())
                    .child("Tasks")
                    .child(task.nodeId.toString()).setValue(Task(id = task.id, title = task.title, description = task.description,
                        startDate = task.startDate, endDate = task.endDate, status = task.status))
            }

        }
    }

}