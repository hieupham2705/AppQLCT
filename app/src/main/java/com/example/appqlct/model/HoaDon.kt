package com.example.appqlct.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("HoaDon")
data class HoaDon(
    @PrimaryKey(true)
    var Id: Int = 0,
    @ColumnInfo(name = "TongTien")
    var TongTien: Long? = 0,
    @ColumnInfo(name = "Mota")
    var Mota: String? = "null",
    @ColumnInfo(name = "SoLuong")
    var SoLuong: Int? = 0,
    @ColumnInfo(name = "Ngay")
    var Ngay: Int? = 0,
)
