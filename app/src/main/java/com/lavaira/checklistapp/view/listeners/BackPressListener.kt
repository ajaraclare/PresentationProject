package com.lavaira.checklistapp.view.listeners

/****
 * Back press listener for handling back navigation in activity/fragments
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
interface BackPressListener {
    fun onBackPress(): Boolean
}