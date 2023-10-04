package com.lavaira.checklistapp.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lavaira.checklistapp.data.local.AppDatabase
import com.lavaira.checklistapp.data.local.dao.TasksDao
import com.lavaira.checklistapp.data.remote.api.Api
import com.lavaira.checklistapp.repository.AuthRepository
import com.lavaira.checklistapp.repository.UserRepository
import com.lavaira.checklistapp.schedulers.SchedulerContract
import com.lavaira.checklistapp.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/****
 * Application Module which is responsible for providing the app wide instances.
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
@Module(includes = [(ViewModelModule::class)])
class AppModule {


    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) : Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: Api, tasksDao: TasksDao): UserRepository {
        return UserRepository(api, tasksDao)
    }


    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth, phoneAuthProvider: PhoneAuthProvider) : AuthRepository{
        return AuthRepository(auth, phoneAuthProvider)
    }


    @Provides
    @Singleton
    fun provideDatabaseReference(): DatabaseReference{
        val database = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)
        database.getReference("Users").child("Tasks").keepSynced(true)
        return database.reference
    }


    @Provides
    @Singleton
    fun provideScheduler(): SchedulerContract {
        return SchedulerProvider()
    }

    /**
     * Provides app AppDatabase
     */
    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "checklists-db").allowMainThreadQueries().build()
    }


    /**
     * Provides NewsArticlesDao an object to access NewsArticles table from Database
     */
    @Singleton
    @Provides
    fun providerTaskDao(db: AppDatabase): TasksDao {
        return db.tasksDao()
    }


    /**
     * Application application level context.
     */
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

}