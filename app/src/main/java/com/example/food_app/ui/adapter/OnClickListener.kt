package com.example.food_app.ui.adapter

import com.example.food_app.room.entity.Food

interface OnClickListener {
    fun update(food: Food, position: Int)
}