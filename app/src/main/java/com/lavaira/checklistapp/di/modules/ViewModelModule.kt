package com.lavaira.checklistapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lavaira.checklistapp.architecture.ViewModelFactory
import com.lavaira.checklistapp.di.key.ViewModelKey
import com.lavaira.checklistapp.view.fragment.dashboard.AddTaskViewModel
import com.lavaira.checklistapp.view.fragment.dashboard.DashboardViewModel
import com.lavaira.checklistapp.view.fragment.landing.LandingViewModel
import com.lavaira.checklistapp.view.fragment.registration.RegistrationViewModel
import com.lavaira.checklistapp.view.fragment.registration.VerificationViewModel
import com.lavaira.checklistapp.viewmodel.EmptyViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/****
 * View Model module which provides the viewmodelfactory and viewmodel instances
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
@Module
interface ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(LandingViewModel::class)
    fun bindLandingViewModel(landingViewModel: LandingViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(EmptyViewModel::class)
    fun bindEmptyViewModel(emptyViewModel: EmptyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VerificationViewModel::class)
    fun bindVerificationViewModel(verificationViewModel: VerificationViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    fun bindDashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTaskViewModel::class)
    fun bindAddTaskViewModel(addTaskViewModel: AddTaskViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}