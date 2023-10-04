package com.lavaira.checklistapp.di.modules

import com.lavaira.checklistapp.common.Configuration
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/****
 * Url Module
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
@Module
class UrlModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return Configuration.baseURL
    }
}