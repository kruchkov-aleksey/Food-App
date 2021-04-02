package com.example.food_app.repo

import com.example.food_app.room.entity.Food
import io.reactivex.Flowable
import io.reactivex.Single

interface OrderRepoI {
    fun update(food: Food): Single<Int>

    fun getCartItem(): Flowable<List<Food>>

    fun getAllItem(): Flowable<List<Food>>
}