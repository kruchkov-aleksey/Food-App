package com.example.food_app.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.food_app.room.dao.FoodDao
import com.example.food_app.room.entity.Food
import com.example.food_app.ui.worker.DataInitializer

@Database(
    entities = [Food::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object{

        private const val TAG ="AppDataBase"
        private const val DATABASE_NAME = "food_application.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase?{
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    ).addCallback(object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<DataInitializer>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }).build()
                }
            }
            return INSTANCE
        }
    }
}