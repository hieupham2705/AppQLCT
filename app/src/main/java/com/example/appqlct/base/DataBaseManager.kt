package com.example.appqlct.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appqlct.dao.DaoRevenue
import com.example.appqlct.dao.DaoSpending
import com.example.appqlct.model.Revenue
import com.example.appqlct.model.Spending

@Database(entities = [Spending::class, Revenue::class], version = 1, exportSchema = false)
abstract class DataBaseManager : RoomDatabase() {
    abstract fun getItemSpendingDAO(): DaoSpending
    abstract fun getItemRevenueDAO(): DaoRevenue

    companion object {
        @Volatile
        private var INSTANCE: DataBaseManager? = null

        fun getInstance(context: Context): DataBaseManager {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseManager::class.java,
                    "DataBase"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}