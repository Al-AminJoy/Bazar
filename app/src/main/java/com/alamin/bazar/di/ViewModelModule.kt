package com.alamin.bazar.di

import androidx.lifecycle.ViewModel
import com.alamin.bazar.view_model.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(UserViewModel::class)
    @IntoMap
    abstract fun provideUserViewModel(userViewModel: UserViewModel): ViewModel
}