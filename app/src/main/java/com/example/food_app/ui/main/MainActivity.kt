package com.example.food_app.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.DataBinderMapperImpl
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.R
import com.example.food_app.databinding.ActivityMainBinding
import com.example.food_app.extension.createFactory
import com.example.food_app.repo.OrderRepoI
import com.example.food_app.room.entity.Food
import com.example.food_app.ui.MainViewModel
import com.example.food_app.ui.adapter.FoodAdapter
import com.example.food_app.ui.adapter.OnClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: FoodAdapter
    private lateinit var binding: ActivityMainBinding

    private var foodList: List<Food> = listOf()

    private var itemPosition: Int = 0

    @Inject
    lateinit var orderRepoI: OrderRepoI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
        getAllList()
        setObserver()
        setRecyclerView()
    }

    private fun init(){
        Log.d(TAG," >>> Initializing viewModel")

        val factory = MainViewModel(orderRepoI).createFactory()
        viewModel =ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun getAllList(){
        viewModel.getOrderList()
    }

    private fun setObserver(){
        viewModel.observableState.observe(this, Observer {
            when{
                it.success ->{
                    Handler().postDelayed({
                        binding.state = it
                    }, 700)

                    adapter.result = it.list!!
                    adapter.notifyItemChanged(itemPosition)
                    calculateTotalItem(it.list!!)
                }
                else -> binding.state = it
            }
        })
    }

    private fun calculateTotalItem(list: List<Food>){
        var totalItem = 0
        list.forEach {
            totalItem += it.quantity!!
        }
        binding.totalItem = totalItem.toString()
    }

    private fun setRecyclerView(){
        rv_food.layoutManager = LinearLayoutManager(this)
        adapter = FoodAdapter(foodList, object: OnClickListener{
            override fun update(food: Food, position: Int) {
                itemPosition = position
                viewModel.updateItem(food)
            }
        })
        rv_food.adapter = adapter
        rv_food.itemAnimator = null
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}