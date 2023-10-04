package com.lavaira.checklistapp.utils

import android.text.TextUtils

/****
 * Keep all validation utils methods here
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/
object Validator {
    /**
     * @param target - this the string of validating the email
     * @return       - return the validation true or false
     */
    fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

}