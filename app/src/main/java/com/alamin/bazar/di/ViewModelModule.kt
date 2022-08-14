package com.alamin.bazar.di

import androidx.lifecycle.ViewModel
import com.alamin.bazar.view_model.CartViewModel
import com.alamin.bazar.view_model.ProductViewModel
import com.alamin.bazar.view_model.UserDataViewModel
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

    @Binds
    @ClassKey(UserDataViewModel::class)
    @IntoMap
    abstract fun provideUserDataViewModel(userDataViewModel: UserDataViewModel): ViewModel

    @Binds
    @ClassKey(ProductViewModel::class)
    @IntoMap
    abstract fun provideProductViewModel(productViewModel: ProductViewModel): ViewModel

    @Binds
    @ClassKey(CartViewModel::class)
    @IntoMap
    abstract fun provideCartViewModel(cartViewModel: CartViewModel): ViewModel
}