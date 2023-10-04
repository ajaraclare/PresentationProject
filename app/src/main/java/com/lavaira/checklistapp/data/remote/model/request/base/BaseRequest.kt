package com.lavaira.checklistapp.data.remote.model.request.base

/****
 * Base Request Model
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
abstract class BaseRequest {
    abstract fun params(): HashMap<String, String>
}