package com.example.food_app.di

import android.app.Application
import com.example.food_app.FoodApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        RepoModule::class,
        RoomModule::class
    ]
)
interface AppComponent: AndroidInjector<FoodApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}