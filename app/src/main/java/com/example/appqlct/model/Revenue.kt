package com.example.appqlct.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity("tableRevenue")
data class Revenue(
    @PrimaryKey(true)
    var id: Int = 0,
    var day: String = "null",
    var note: String = "null",
    var money: String = "null",
    var directory: String = "null"
)
