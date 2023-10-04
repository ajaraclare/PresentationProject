package com.lavaira.checklistapp.di.modules

import com.lavaira.checklistapp.view.fragment.dashboard.AddTaskDialogFragment
import com.lavaira.checklistapp.view.fragment.dashboard.DashboardFragment
import com.lavaira.checklistapp.view.fragment.landing.LandingFragment
import com.lavaira.checklistapp.view.fragment.registration.RegistrationFragment
import com.lavaira.checklistapp.view.fragment.registration.VerifyOtpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/****
 * The module which provides the android injection service to fragments.
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
@Suppress("unused")
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeLandingFragment(): LandingFragment

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    abstract fun contributeVerifyOtpFragment(): VerifyOtpFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeAddTaskDialogFragment(): AddTaskDialogFragment
}