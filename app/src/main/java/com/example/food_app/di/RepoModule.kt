package com.example.food_app.di

import com.example.food_app.repo.OrderRepo
import com.example.food_app.repo.OrderRepoI
import com.example.food_app.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideOrderRepo(appDatabase: AppDatabase):OrderRepoI{
        return OrderRepo(appDatabase)
    }
}