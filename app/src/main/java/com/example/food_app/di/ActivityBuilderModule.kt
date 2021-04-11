package com.example.food_app.di

import com.example.food_app.ui.cart.CartActivity
import com.example.food_app.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainActivityProvider(): MainActivity

    @ContributesAndroidInjector
    abstract fun cartActivityProvider(): CartActivity
}