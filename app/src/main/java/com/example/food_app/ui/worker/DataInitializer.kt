package com.example.food_app.ui.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.food_app.room.database.AppDatabase
import com.example.food_app.room.entity.Food
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

class DataInitializer(context: Context,workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        var result = Result.failure()
        try{
            applicationContext.assets.open("foods.json").use{
                JsonReader(it.reader()).use{ jsonReader ->
                    val plantType = object:TypeToken<List<Food>>() {}.type
                    val plantList: List<Food> = Gson().fromJson(jsonReader, plantType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database?.foodDao()?.insertAll(plantList)
                    result = Result.success()
                }
            }
        }catch(ex:Exception){
            Log.e(TAG,"Error in data base initialization into database", ex)
            result = Result.failure()
        }
        return result
    }
    companion object{
        private const val TAG = "SeedDatabaseWorker"
    }
}