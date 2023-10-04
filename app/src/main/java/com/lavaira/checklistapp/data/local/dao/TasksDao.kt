package com.lavaira.checklistapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lavaira.checklistapp.data.remote.model.response.tasks.Task

/****
 * Abstracts access to Tasks database
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 2020-03-16
 * Modified on: 2020-03-16
 *****/
@Dao
interface TasksDao {
    /**
     * Insert an individual task into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserTask(task: Task)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTasks(tasksList: List<Task>)

    /**
     * Get all the articles from database
     */
    @Query("SELECT * FROM tasks_table")
    fun getTasks(): LiveData<List<Task>>


    @Query("UPDATE tasks_table SET title=:title, description=:desc, startdate=:startDate, endDate=:endDate, status=:status WHERE nodeId=:nodeId")
    fun updateTask(title: String, desc: String, startDate: String, endDate: String, status: String, nodeId: String)


    @Query("DELETE FROM tasks_table WHERE nodeId =:node")
    fun deleteTask(node: String)

    @Query("DELETE FROM tasks_table")
    abstract fun deleteAllTasks()
}