package com.example.food_app.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.R
import com.example.food_app.databinding.ActivityCartBinding
import com.example.food_app.extension.createFactory
import com.example.food_app.repo.OrderRepoI
import com.example.food_app.room.entity.Food
import com.example.food_app.ui.MainViewModel
import com.example.food_app.ui.adapter.FoodAdapter
import com.example.food_app.ui.adapter.OnClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_cart.*
import javax.inject.Inject

class CartActivity: DaggerAppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    private lateinit var adapter: FoodAdapter

    private lateinit var viewModel: MainViewModel

    private var cartList: List<Food> = listOf()

    private var itemPosition: Int = 0

    @Inject
    lateinit var orderRepoI: OrderRepoI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        init()
        getCartList()
        setObserver()
        setRecyclerView()
        onClicks()
        
    }


    private fun init(){
        Log.d(TAG, " >>> Initializing viewModel")

        val factory =MainViewModel(orderRepoI).createFactory()
        viewModel =ViewModelProvider(this, factory).get(MainViewModel:: class.java)
    }

    private fun getCartList(){
        viewModel.getCartList()
    }

    private fun setObserver(){
        viewModel.observableState.observe(this,{
            when{
                it.success ->{
                    Handler().postDelayed({
                        binding.state = it
                    }, 700)
                    cartList = it.list!!
                    if(cartList.size > 2){
                        adapter.result = listOf(cartList[0], cartList[1])
                        binding.shouldShowMore = true
                    }else{
                        adapter.result = cartList
                        binding.shouldShowMore = false
                    }
                    adapter.notifyItemChanged(itemPosition)
                    calculateTotalPrice(cartList)
                }
                else ->binding.state = it
            }
        })
    }
    private fun setRecyclerView(){
        rv_cart.layoutManager = LinearLayoutManager(this)
        adapter = FoodAdapter(cartList, object: OnClickListener{
            override fun update(food: Food, position: Int) {
                itemPosition =position
                viewModel.updateItem(food)
            }
        })
        rv_cart.adapter = adapter
        rv_cart.itemAnimator = null
    }

    private fun onClicks(){
        view_place_order_bottom.setOnClickListener { }
        tv_show_more.setOnClickListener {
            binding.shouldShowMore = false
            adapter.result = cartList
            adapter.notifyDataSetChanged()
        }
        btn_back.setOnClickListener {
            onBackPressed()
        }
    }
    private fun calculateTotalPrice(cartList: List<Food>?){
        var totalPrice = 0
        cartList?.let {list ->
            list.forEach {
                if(it.quantity != 0){
                    totalPrice += it.rate.toInt() * it.quantity!!
                }
            }
        }
        binding.totalPrice = String.format(getString(R.string.total_price), totalPrice.toString())
    }
    companion object{
        const val TAG = "CartActivity"
        fun start(context: Context){
            context.startActivity(Intent(context, CartActivity::class.java ))
        }
    }
}