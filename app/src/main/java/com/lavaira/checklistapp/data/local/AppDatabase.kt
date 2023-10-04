package com.lavaira.checklistapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lavaira.checklistapp.data.local.dao.TasksDao
import com.lavaira.checklistapp.data.remote.model.response.tasks.Task

/****
 * App Database
 * Define all entities and access doa's here/ Each entity is a table.
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-16
 * Modified on: 2020-03-16
 *****/
@Database(entities = [Task::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun tasksDao() : TasksDao
}