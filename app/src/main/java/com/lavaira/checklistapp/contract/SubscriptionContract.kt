package com.lavaira.checklistapp.contract

/****
 * Subscription contract. Wherever you want to subcribe for the network response
 * or navigation event, implement this interface
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
interface SubscriptionContract {
    fun subscribeNetworkResponse(){
        // Implementation goes in the owner
    }
    fun subscribeNavigationEvent(){
        // Implementation goes in the owner
    }
    fun unsubscribe(){
        // Implementation goes in the owner
    }
}