package com.example.food_app.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_app.repo.OrderRepoI
import com.example.food_app.room.entity.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val orderRepoI: OrderRepoI):ViewModel() {

    var observableState: MutableLiveData<MainState> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    private var state = MainState()
        set(value) {
            field = value
            publishState(value)
        }

    fun updateItem(food: Food){
        compositeDisposable.add(
            orderRepoI.update(food)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun getOrderList(){
        Log.d(TAG, " >>> Received call to target order list")
        state = state.copy(loading = true, success = false, failure = false, list = null)
        compositeDisposable.add(
            orderRepoI.getAllItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state = state.copy(loading = false, success = true, failure = false, list = it)
                },{
                    state = state.copy(loading = false, success = false, failure = false, message = it.localizedMessage)
                }

                )
        )
    }
    private fun publishState(state: MainState){
        Log.d(TAG, ">>> Publish state:, $state")
        observableState.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, " >> Clearing compositeDisposable")
        compositeDisposable.clear()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}