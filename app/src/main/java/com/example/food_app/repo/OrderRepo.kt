package com.example.food_app.repo

import com.example.food_app.room.database.AppDatabase
import com.example.food_app.room.entity.Food
import io.reactivex.Flowable
import io.reactivex.Single

class OrderRepo(private val db: AppDatabase): OrderRepoI {

    override fun update(food: Food): Single<Int> {
        return db.foodDao().updateFood(food)
    }

    override fun getCartItem(): Flowable<List<Food>> {
        return db.foodDao().getCartList()
    }

    override fun getAllItem(): Flowable<List<Food>> {
        return db.foodDao().getAllList()
    }
}