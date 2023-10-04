package com.lavaira.checklistapp.data.remote

import com.lavaira.checklistapp.common.AppSession
import com.lavaira.checklistapp.data.remote.api.ResponseListener
import com.lavaira.checklistapp.data.remote.api.ResponseStatus.SUCCESS
import com.lavaira.checklistapp.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


/****
 * Token TokenAuthenticator which take care of refreshing the identity token when it is expired.
 * The authenticate method will be invoked automatically in case if we get HTTP 401 (),
 * and the token will be refreshed and the failed request will be retried again with the new token
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-16
 * Modified on: 2020-03-16
 *****/
class TokenAuthenticator @Inject constructor(private val authRepository: AuthRepository) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        var updatedRequest: Request? = response.request

        if(!AppSession.idToken.isNullOrEmpty()){
            val modeifiedUrl = response.request.url.newBuilder()
                .addQueryParameter("auth", AppSession.idToken).build()
            updatedRequest =
                response.request.newBuilder().url(modeifiedUrl).build()
            return updatedRequest
        }

        synchronized(this) {
            if (response.code == 401) {
                runBlocking {
                    authRepository.getIdentityToken(object : ResponseListener<String> {
                        override fun onStart() {
                            // Nothing goes here
                        }

                        override fun onFinish() {
                            // Nothing goes here
                        }

                        override fun onResponse(result: com.lavaira.checklistapp.data.remote.api.Response<String>) {
                            if (result.status == SUCCESS) {
                                AppSession.idToken = result.data
                            }
                        }

                    })
                }

            }
            return updatedRequest
        }

    }
}