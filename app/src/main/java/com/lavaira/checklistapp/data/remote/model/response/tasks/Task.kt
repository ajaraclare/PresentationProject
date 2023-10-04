package com.lavaira.checklistapp.data.remote.model.response.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lavaira.checklistapp.common.Constants
import com.lavaira.checklistapp.data.remote.model.request.base.BaseRequest

/****
 * Model for representing a user task
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-16
 * Modified on: 2020-03-16
 *****/

@Entity(tableName = "tasks_table")
data class Task (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("nodeId") var nodeId: String? = "",
    @SerializedName("title") var title: String? = "",
    @SerializedName("description") var description: String? = "",
    @SerializedName("startDate") var startDate: String? = "",
    @SerializedName("endDate") var endDate: String? = "",
    @SerializedName("status") var status: String? = ""
) : BaseRequest(){
    override fun params(): HashMap<String, String> {
        val taskParams = HashMap<String, String>()
        taskParams[Constants.TASK_ATTRIBUTES.TITLE] = title.toString()
        taskParams[Constants.TASK_ATTRIBUTES.DESC] = description.toString()
        taskParams[Constants.TASK_ATTRIBUTES.START_DATE] = startDate.toString()
        taskParams[Constants.TASK_ATTRIBUTES.END_DATE] = endDate.toString()
        taskParams[Constants.TASK_ATTRIBUTES.STATUS]= status.toString()
        taskParams[Constants.TASK_ATTRIBUTES.ID] = id.toString()
        return taskParams
    }
}