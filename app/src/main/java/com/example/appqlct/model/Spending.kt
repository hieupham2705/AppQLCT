package com.example.appqlct.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity("TableSpending")
data class Spending(
    @PrimaryKey(true)
    var id : Int = 0,
    var day: String = "null",
    var note: String = "null",
    var money: String = "null",
    var directory: String= "null"
)

