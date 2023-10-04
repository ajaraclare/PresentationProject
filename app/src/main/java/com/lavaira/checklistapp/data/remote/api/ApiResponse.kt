package com.lavaira.checklistapp.data.remote.api

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.stream.MalformedJsonException
import com.lavaira.checklistapp.ChecklistApplication
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.common.Constants
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.regex.Pattern

/****
 * Data model class which represents the API Response
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
sealed class ApiResponse<T> {
    companion object {
        val context: Context = ChecklistApplication.applicationContext()!!

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            error.let {
                when (it) {

                    is SocketTimeoutException -> {
                        return ApiErrorResponse(error, context.getString(R.string.requestTimeOutErrorCode),
                            context.getString(R.string.requestTimeOutError))
                    }

                    is UnknownHostException -> {
                        return ApiErrorResponse(error, context.getString(R.string.networkErrorCode), context.getString(R.string.networkError))
                    }

                    is MalformedJsonException -> {
                        return ApiErrorResponse(error, context.getString(R.string.errorCodeMalformedJson), context.getString(R.string.responseMalformedJson))
                    }
                    else -> {
                        return ApiErrorResponse(error, "", context.getString(R.string.unknownError))
                    }
                }
            }

        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body,
                        error = Throwable()
                    )
                }
            } else {

                val responseBody: String? = response.errorBody()?.string();

                if (hasHTMLTags(responseBody)) {
                    return ApiErrorResponse(HttpException(response), "", context.getString(R.string.unknownError))
                }

                try {
                    val apiErrorResponse: ErrorResponses =
                        Gson().fromJson(responseBody, ErrorResponses::class.java)

                    if(TextUtils.isEmpty(apiErrorResponse.responseMessage)) {
                        return ApiErrorResponse(HttpException(response), apiErrorResponse.responseCode, context.getString(R.string.unknownError))
                    }

                    return ApiErrorResponse(HttpException(response), apiErrorResponse.responseCode, apiErrorResponse.responseMessage)
                } catch (ex: Exception){
                    return ApiErrorResponse(HttpException(response), "", context.getString(R.string.unknownError))
                }

            }
        }

        private fun hasHTMLTags(responseBody: String?): Boolean {
            val pattern = Pattern.compile(Constants.HTML_PATTERN)
            val matcher = pattern.matcher(responseBody)
            return matcher.find()
        }
    }
}

/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T, val error: Throwable) : ApiResponse<T>()

data class ApiErrorResponse<T>(val error: Throwable, val errorCode: String, val errorDescription: String) : ApiResponse<T>()

data class ErrorResponses(
    val responseCode: String = "",
    val responseMessage: String = ""
)