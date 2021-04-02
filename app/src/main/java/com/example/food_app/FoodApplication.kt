package com.example.food_app

import android.util.Log
import com.example.food_app.di.DaggerAppComponent
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class FoodApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        Log.d(TAG, ">>> FoodApplication Created")

        if(BuildConfig.DEBUG){
            Log.d(TAG, ">> Initialize Stetho")
            Stetho.initializeWithDefaults(this)
        }

        return DaggerAppComponent.builder().application(this).build()
    }

    companion object{
        const val TAG = "FoodApplication"
    }
}