package com.example.food_app.ui

import com.example.food_app.room.entity.Food

data class MainState(
    var loading: Boolean = false,
    var success: Boolean = false,
    var failure:Boolean = false,
    var message: String? = null,
    var list: List<Food>? = listOf()
)