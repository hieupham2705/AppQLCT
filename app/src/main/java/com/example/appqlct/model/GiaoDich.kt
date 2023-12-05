package com.example.appqlct.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.PropertyKey

@Entity(
    "GiaoDich", foreignKeys = [ForeignKey(
        entity = DanhMuc::class,
        parentColumns = ["Id"],
        childColumns = ["IdDanhMuc"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class GiaoDich(
    @PrimaryKey(true)
    var Id: Int = 0,
    @ColumnInfo(name = "NgayGiaoDich")
    var ngayGiaoDich: Int? = 0,
    @ColumnInfo(name = "ThangGiaoDich")
    var thangGiaoDich: Int? = 0,
    @ColumnInfo(name = "NamGiaoDich")
    var namGiaoDich: Int? = 0,
    @ColumnInfo(name = "Tien")
    var tien: Long? = 0,
    @ColumnInfo(name = "GhiChu")
    var ghiChu: String? = "null",
    @ColumnInfo(name = "ThuChi")
    var thuChi: Boolean? = true,
    // khoa ngoai
    @ColumnInfo(name = "IdDanhMuc")
    var idDanhMuc: Int? = 0
)
