package com.example.appqlct.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appqlct.model.Spending


@Dao
interface DaoSpending {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(spending: Spending)
    @Query("select * from tablespending where day = :day")
    suspend fun search(day : String):List<Spending>
}