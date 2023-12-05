package com.example.appqlct.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.PropertyKey

@Entity(
    "NguoiDung", foreignKeys = [ForeignKey(
        entity = DanhMuc::class,
        parentColumns = ["Id"],
        childColumns = ["IdHoaDon"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class NguoiDung(
    @PrimaryKey(true)
    val id: Int = 0,
    @ColumnInfo(name = "Ten")
    val Ten: String? = "null",
    @ColumnInfo(name = "Sdt")
    val Sdt: String? = "null",
    @ColumnInfo(name = "KhoanChi")
    val KhoanChi: String? = "null",
    @ColumnInfo(name = "TrangThai")
    val TrangThai: String? = "null",
    // khoa ngoai
    @ColumnInfo(name = "IdHoaDon")
    val IdHoaDon: Int? = 0
)
