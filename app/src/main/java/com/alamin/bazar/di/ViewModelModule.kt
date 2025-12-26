package com.alamin.bazar.di

import androidx.lifecycle.ViewModel
import com.alamin.bazar.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(MainViewModel::class)
    @IntoMap
    abstract fun provideMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @ClassKey(LoginViewModel::class)
    @IntoMap
    abstract fun provideLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @ClassKey(SignUpViewModel::class)
    @IntoMap
    abstract fun provideSignUpViewModel(viewModel: SignUpViewModel): ViewModel


    @Binds
    @ClassKey(ProductDetailsViewModel::class)
    @IntoMap
    abstract fun provideProductDetailsViewModel(viewModel: ProductDetailsViewModel): ViewModel

    @Binds
    @ClassKey(DashboardViewModel::class)
    @IntoMap
    abstract fun provideProductViewModel(dashboardViewModel: DashboardViewModel): ViewModel

    @Binds
    @ClassKey(CartViewModel::class)
    @IntoMap
    abstract fun provideCartViewModel(cartViewModel: CartViewModel): ViewModel

    @Binds
    @ClassKey(InvoiceViewModel::class)
    @IntoMap
    abstract fun provideInvoiceViewModel(invoiceViewModel: InvoiceViewModel): ViewModel

    @Binds
    @ClassKey(WishViewModel::class)
    @IntoMap
    abstract fun provideWishlistViewModel(wishViewModel: WishViewModel): ViewModel

    @Binds
    @ClassKey(OrderDetailsViewModel::class)
    @IntoMap
    abstract fun provideOrderDetailsViewModel(viewModel: OrderDetailsViewModel): ViewModel

    @Binds
    @ClassKey(OrdersViewModel::class)
    @IntoMap
    abstract fun provideOrdersViewModel(viewModel: OrdersViewModel): ViewModel

    @Binds
    @ClassKey(EditProfileViewModel::class)
    @IntoMap
    abstract fun provideUserViewModel(editProfileViewModel: EditProfileViewModel): ViewModel


}