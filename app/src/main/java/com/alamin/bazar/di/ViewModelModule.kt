package com.alamin.bazar.di

import androidx.lifecycle.ViewModel
import com.alamin.bazar.view_model.*
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
    @ClassKey(ProductViewModel::class)
    @IntoMap
    abstract fun provideProductViewModel(productViewModel: ProductViewModel): ViewModel

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
    abstract fun provideWishlist(wishViewModel: WishViewModel): ViewModel
}