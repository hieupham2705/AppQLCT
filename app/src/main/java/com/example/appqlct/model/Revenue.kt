package com.example.appqlct.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity("tableRevenue")
data class Revenue(
    @PrimaryKey(true)
    var id: Int = 0,
    var day: String = "null",
    var month: Int = 1,
    var note: String = "null",
    var money: Long = 0,
    var directory: String = "null"
)
