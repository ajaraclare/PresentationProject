package com.lavaira.checklistapp.common

import androidx.annotation.IntDef

/****
 * Keep all the Build/ deployment configurations here
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
object Configuration {

    private const val SIT = 0

    private const val DEV = 1

    private const val UAT = 2

    private const val PROD = 3

    @DeploymentType
    private val defaultEnvironment = DEV

    const val API_KEY = "AIzaSyBnVtRwKKCLQ1D_lSEd9bI4ynX1UqF_WSc"


    const val AUTH_ENDPOINT = "https://identitytoolkit.googleapis.com/v1/accounts=$API_KEY"


    // HOST Urls
    private const val URL_SIT = "https://checklistapp-b0556.firebaseio.com/" //Put the SIT url here

    private const val URL_DEV = "https://checklistapp-b0556.firebaseio.com/" // Put the development url here

    private const val URL_PROD = "https://checklistapp-b0556.firebaseio.com/" // Put the production url here

    private const val URL_UAT = "https://checklistapp-b0556.firebaseio.com/" // Put the UAT url here

    val baseURL: String
        get() {

            return when (defaultEnvironment) {

                SIT -> URL_SIT

                DEV -> URL_DEV

                UAT -> URL_UAT

                PROD -> URL_PROD

                else -> URL_DEV
            }
        }


    @IntDef(SIT, DEV, UAT, PROD)
    private annotation class DeploymentType
}