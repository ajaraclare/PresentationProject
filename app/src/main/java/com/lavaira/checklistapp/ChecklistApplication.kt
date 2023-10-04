package com.lavaira.checklistapp

import android.app.Activity
import android.app.Application
import android.content.Context
import com.lavaira.checklistapp.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber
import javax.inject.Inject

/****
 * Application class.
 * All the application wide intialization should be done here
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
class ChecklistApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    init {
        instance = this
    }

    companion object {
        var instance: ChecklistApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Poppins-Regular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()))
                .build()
        )

        AppInjector.init(this)
    }


    override fun activityInjector() = dispatchingAndroidInjector
}