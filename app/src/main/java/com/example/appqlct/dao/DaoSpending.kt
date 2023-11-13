package com.example.appqlct.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appqlct.model.Spending
import java.time.Month


@Dao
interface DaoSpending {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(spending: Spending)
    @Query("select * from tableSpending where day = :day")
    suspend fun search(day : String):List<Spending>
    @Query("select * from tableSpending where month = :month")
    suspend fun searchSpending(month: Int):List<Spending>
    @Query("select money from tableSpending where month = :month")
    suspend fun searchMoneySpending(month: Int):List<Long>
}