package com.example.appqlct.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.PropertyKey

@Entity(
    "NguoiDung", foreignKeys = [ForeignKey(
        entity = HoaDon::class,
        parentColumns = ["Id"],
        childColumns = ["IdHoaDon"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class NguoiDung(
    @PrimaryKey(true)
    val id: Int = 0,
    @ColumnInfo(name = "Ten")
    val ten: String? = "null",
    @ColumnInfo(name = "Sdt")
    val sdt: String? = "null",
    @ColumnInfo(name = "KhoanChi")
    val khoanChi: Long? = 0,
    @ColumnInfo(name = "TrangThai")
    val trangThai: Boolean? = true,
    // khoa ngoai
    @ColumnInfo(name = "IdHoaDon")
    val idHoaDon: Int? = 0
)
