package com.lavaira.checklistapp.data.remote.api

import retrofit2.Retrofit

/**
 * Synthetic sugaring to create Retrofit Service.
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
inline fun <reified T> Retrofit.create(): T = create(T::class.java)

