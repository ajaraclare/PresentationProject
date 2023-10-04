package com.lavaira.checklistapp.data.remote.model.response.tasks

import com.google.gson.annotations.SerializedName

/****
 * Tasks Response
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 2020-03-17
 * Modified on: 2020-03-17
 *****/
data class TasksResponse(@SerializedName("tasks") var tasks: List<Task> = emptyList())