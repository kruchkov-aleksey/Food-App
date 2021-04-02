package com.example.food_app.di

import android.app.Application
import com.example.food_app.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase( applicationContext: Application):AppDatabase{
        return AppDatabase.getInstance(applicationContext)!!
    }
}