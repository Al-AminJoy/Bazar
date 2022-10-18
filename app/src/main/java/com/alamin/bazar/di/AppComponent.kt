package com.alamin.bazar.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.alamin.bazar.view.activity.LoginActivity
import com.alamin.bazar.view.activity.MainActivity
import com.alamin.bazar.view.activity.SignUpActivity
import com.alamin.bazar.view.dialog.CustomAddressFragment
import com.alamin.bazar.view.fragment.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [LocalStorageModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun injectMain(mainActivity: MainActivity)
    fun injectLogin(loginActivity: LoginActivity)
    fun injectSignUp(signUpActivity: SignUpActivity)
    fun injectDashBoard(dashBoardFragment: DashBoardFragment)
    fun injectProductDetails(productDetailsFragment: ProductDetailsFragment)
    fun injectCart(cartFragment: CartFragment)
    fun injectOrders(ordersFragment: OrdersFragment)
    fun injectProfile(profileFragment: ProfileFragment)
    fun injectRegister(registrationFragment: RegistrationFragment)
    fun injectWishList(wishListFragment: WishListFragment)
    fun injectCheckout(checkoutFragment: CheckoutFragment)
    fun injectCustomAddress(customAddressFragment: CustomAddressFragment)
    fun injectOrderDetails(orderDetailsFragment: OrderDetailsFragment)
    fun injectEditProfile(editProfileFragment: EditProfileFragment)

    fun getViewModelMap(): Map<Class<*>, ViewModel>

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}