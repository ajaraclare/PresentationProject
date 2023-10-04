package com.lavaira.checklistapp.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/****
 * Firebase Authentication Module
 * Author: Lajesh Dineshkumar
 * Created on: 2020-03-15
 * Modified on: 2020-03-15
 *****/

@Module
class FirebaseAuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providePhoneAuth() : PhoneAuthProvider{
        return PhoneAuthProvider.getInstance()
    }
}