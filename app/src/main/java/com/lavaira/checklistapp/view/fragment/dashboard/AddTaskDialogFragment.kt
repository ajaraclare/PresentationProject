package com.lavaira.checklistapp.view.fragment.dashboard

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import cafe.adriel.kbus.KBus
import com.lavaira.checklistapp.BR
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.contract.SubscriptionContract
import com.lavaira.checklistapp.data.remote.model.EventMessage
import com.lavaira.checklistapp.databinding.DialogFragmentAddTaskBinding
import com.lavaira.checklistapp.view.fragment.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_fragment_add_task.*
import java.util.*


/****
 * Add Task Dialog fragment
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-16
 * Modified on: 2020-03-16
 *****/
class AddTaskDialogFragment : BaseBottomSheetDialogFragment<AddTaskViewModel, DialogFragmentAddTaskBinding>(),
SubscriptionContract{
    override val layoutRes: Int
        get() = R.layout.dialog_fragment_add_task
    override val bindingVariable: Int
        get() = BR.viewModel
    override val subscriptionContract: SubscriptionContract?
        get() = this
    override fun getViewModel(): Class<AddTaskViewModel> {
        return AddTaskViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData()
        if(viewModel.isInEditMode.value!!){
            btnDelete.visibility = View.VISIBLE
            btnStatus.visibility = View.VISIBLE
        }else{
            btnDelete.visibility = View.GONE
            btnStatus.visibility = View.GONE
        }
        etStartDate.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val month: Int = c.get(Calendar.MONTH)
            val day: Int = c.get(Calendar.DAY_OF_MONTH)
            val year: Int = c.get(Calendar.YEAR)

            val datePickerDialog =
                DatePickerDialog(activity as FragmentActivity,
                    OnDateSetListener { view, year, month, dayOfMonth -> etStartDate.setText("$dayOfMonth/${month +1}/$year") },
                    year,
                    month,
                    day
                )
            datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.show()
        }


        etEndDate.setOnClickListener{
            val c: Calendar = Calendar.getInstance()
            val month: Int = c.get(Calendar.MONTH)
            val day: Int = c.get(Calendar.DAY_OF_MONTH)
            val year: Int = c.get(Calendar.YEAR)

            val datePickerDialog =
                DatePickerDialog(activity as FragmentActivity,
                    OnDateSetListener { view, year, month, dayOfMonth -> etEndDate.setText("$dayOfMonth/${month+1}/$year") },
                    year,
                    month,
                    day
                )
            datePickerDialog.datePicker.minDate =c.timeInMillis
            datePickerDialog.show()
        }

    }

    override fun subscribeNavigationEvent() {
        super.subscribeNavigationEvent()

        viewModel.validationErrorEvent.observe(this, Observer {
            Toast.makeText(activity, "Title, Startdate and End date are mandatory", Toast.LENGTH_SHORT).show()
        })

        viewModel.addTaskResponse.observe(this, Observer {
            when {
                it.status.isLoading() -> {
                    viewModel.loadingStatus.value = true
                }
                else->{
                    viewModel.loadingStatus.value = false
                    KBus.post(EventMessage(""))
                    dismiss()
                }
            }


        })


        viewModel.deleteTaskResponse.observe(this, Observer {
            when {
                it.status.isLoading() -> {
                    viewModel.loadingStatus.value = true
                }else ->{
                    viewModel.loadingStatus.value = false
                    KBus.post(EventMessage(""))
                    dismiss()
                }

            }


        })
    }
}