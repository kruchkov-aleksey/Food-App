package com.example.food_app.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.food_app.R
import com.example.food_app.databinding.ActivityCartBinding
import dagger.android.support.DaggerAppCompatActivity

class CartActivity: DaggerAppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
    }

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, CartActivity::class.java ))
        }
    }
}