package com.lavaira.checklistapp.common

import androidx.annotation.StringDef
import com.lavaira.checklistapp.common.Constants.AUTH_URLS.Companion.RESETPW_URL
import com.lavaira.checklistapp.common.Constants.AUTH_URLS.Companion.SIGNIN_URL
import com.lavaira.checklistapp.common.Constants.AUTH_URLS.Companion.SIGNUP_URL
import com.lavaira.checklistapp.common.Constants.REGISTRATION_ATTRIBUTES.Companion.ADDRESS
import com.lavaira.checklistapp.common.Constants.REGISTRATION_ATTRIBUTES.Companion.EMAIL
import com.lavaira.checklistapp.common.Constants.REGISTRATION_ATTRIBUTES.Companion.FIRST_NAME
import com.lavaira.checklistapp.common.Constants.REGISTRATION_ATTRIBUTES.Companion.LAST_NAME
import com.lavaira.checklistapp.common.Constants.TASK_ATTRIBUTES.Companion.DESC
import com.lavaira.checklistapp.common.Constants.TASK_ATTRIBUTES.Companion.END_DATE
import com.lavaira.checklistapp.common.Constants.TASK_ATTRIBUTES.Companion.ID
import com.lavaira.checklistapp.common.Constants.TASK_ATTRIBUTES.Companion.START_DATE
import com.lavaira.checklistapp.common.Constants.TASK_ATTRIBUTES.Companion.STATUS
import com.lavaira.checklistapp.common.Constants.TASK_ATTRIBUTES.Companion.TITLE
import com.lavaira.checklistapp.common.Constants.TASK_STATUS.Companion.TASK_COMPLETED
import com.lavaira.checklistapp.common.Constants.TASK_STATUS.Companion.TASK_EXPIRED
import com.lavaira.checklistapp.common.Constants.TASK_STATUS.Companion.TASK_INPROGRESS
import com.lavaira.checklistapp.common.Constants.TASK_STATUS.Companion.TASK_TODO

/****
 * Keep all the common constants here
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
object Constants {
    const val QUERY_PARAM__APIKEY = "api-key"
    const val QUERY_PARAM_AUTH = "auth"
    const val PW_LENGTH = 6
    const val BASE_AUTH_URL = "https://identitytoolkit.googleapis.com/v1/accounts:"
    const val HTML_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>"


    @StringDef(EMAIL, FIRST_NAME, LAST_NAME, ADDRESS)
    @Retention(AnnotationRetention.SOURCE)
    annotation class REGISTRATION_ATTRIBUTES {
        companion object {
            const val EMAIL = "email"
            const val FIRST_NAME = "firstname"
            const val LAST_NAME = "lastname"
            const val ADDRESS = "address"
        }
    }


    @StringDef(TASK_TODO,TASK_INPROGRESS, TASK_COMPLETED, TASK_EXPIRED)
    @Retention(AnnotationRetention.SOURCE)
    annotation class TASK_STATUS {
        companion object{
            const val TASK_TODO = "TODO"
            const val TASK_INPROGRESS = "In Progress"
            const val TASK_COMPLETED = "Completed"
            const val TASK_EXPIRED = "Expired"
        }
    }

    @StringDef(ID, TITLE, DESC, START_DATE, END_DATE, STATUS)
    @Retention(AnnotationRetention.SOURCE)
    annotation class TASK_ATTRIBUTES {
        companion object {
            const val ID = "id"
            const val TITLE = "title"
            const val DESC = "description"
            const val START_DATE = "startDate"
            const val END_DATE = "endDate"
            const val STATUS = "status"
        }
    }


    @StringDef(SIGNUP_URL, SIGNIN_URL, RESETPW_URL)
    @Retention(AnnotationRetention.SOURCE)
    annotation class AUTH_URLS{
        companion object{
            const val SIGNUP_URL = "${BASE_AUTH_URL}signUp?key=${Configuration.API_KEY}"
            const val SIGNIN_URL = "${BASE_AUTH_URL}signInWithPassword?key=${Configuration.API_KEY}"
            const val RESETPW_URL = ""
        }
    }
}