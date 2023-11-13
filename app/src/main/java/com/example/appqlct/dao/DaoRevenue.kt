package com.example.appqlct.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appqlct.model.Revenue
import com.example.appqlct.model.Spending
import java.time.Month

@Dao
interface DaoRevenue {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(revenue: Revenue)
    @Query("select * from tableRevenue where day = :day")
    suspend fun search(day : String):List<Revenue>
    @Query("select * from tableRevenue where month = :month")
    suspend fun searchRevenue(month: Int):List<Revenue>
    @Query("select money from tableRevenue where month = :month")
    suspend fun searchMoneyRevenue(month: Int):List<Long>
}