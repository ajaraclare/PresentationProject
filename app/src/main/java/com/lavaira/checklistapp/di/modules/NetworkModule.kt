package com.lavaira.checklistapp.di.modules

import com.lavaira.checklistapp.data.remote.RequestInterceptor
import com.lavaira.checklistapp.data.remote.TokenAuthenticator
import com.lavaira.checklistapp.data.remote.api.LiveDataCallAdapterFactory
import com.lavaira.checklistapp.repository.AuthRepository
import dagger.Module
import dagger.Provides
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/****
 * Network Module which provides the retrofit related instances
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/

@Module
class NetworkModule {
    private val CONNECTION_TIMEOUT: Long = 30000
    private val READ_TIMEOUT: Long = 30000
    private val WRITE_TIMEOUT: Long = 3000

    companion object {
        private const val BASE_URL = "baseUrl"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor, authRepository: AuthRepository): OkHttpClient {
        val cookieHandler = CookieManager()
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(RequestInterceptor())
            .authenticator(TokenAuthenticator(authRepository))
            .cookieJar(JavaNetCookieJar(cookieHandler))
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(@Named(BASE_URL) baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}