package com.example.appqlct.model

data class SpendingInCalendar(
    val avtSpending: Int? = 0,
    val idDirectory: Int? = 0,
    val idGiaoDich: Int? = 0,
    val money: Long? = 0,
    val note: String ?="",
    val check : Boolean ?= true
)
