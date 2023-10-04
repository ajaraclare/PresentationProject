package com.lavaira.checklistapp.data.remote

import com.lavaira.checklistapp.common.AppSession
import com.lavaira.checklistapp.common.Configuration
import com.lavaira.checklistapp.common.Constants
import okhttp3.Interceptor
import okhttp3.Response


/****
 * This okhttp interceptor is responsible for adding the common query parameters and headers
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val urlBuilder = originalHttpUrl.newBuilder()
            .addQueryParameter(Constants.QUERY_PARAM__APIKEY, Configuration.API_KEY)


        if(!AppSession.idToken.isNullOrEmpty()){
            urlBuilder.addQueryParameter(Constants.QUERY_PARAM_AUTH, AppSession.idToken)
        }
        val request = originalRequest.newBuilder().url(urlBuilder.build()).build()
        return chain.proceed(request)
    }
}