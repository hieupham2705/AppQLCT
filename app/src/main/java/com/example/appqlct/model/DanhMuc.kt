package com.example.appqlct.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("DanhMuc")
data class DanhMuc(
    @PrimaryKey(true)
    var Id: Int = 0,
    @ColumnInfo(name = "Icon")
    var icon: String? = "null",
    @ColumnInfo(name = "TenDanhMuc")
    var tenDanhMuc: String? = "null",
    @ColumnInfo(name = "ThuChi")
    var thuChi: Boolean? = true

)
