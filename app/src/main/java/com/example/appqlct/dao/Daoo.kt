package com.example.appqlct.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appqlct.model.DanhMuc
import com.example.appqlct.model.GiaoDich
import com.example.appqlct.model.SpendingInChart

@Dao
interface Daoo {
    // GiaoDichDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun themNguoiGiaoDich(giaoDich: GiaoDich): Long
    @Query("select * from GiaoDich where NgayGiaoDich = :ngay and ThangGiaoDich =:thang and NamGiaoDich = :nam")
    suspend fun timKiemGiaoDichChiTheoNgayThangNam(ngay: Int, thang: Int, nam: Int): List<GiaoDich>

    @Query("select * from GiaoDich where NgayGiaoDich = :ngay and ThuChi = 1")
    suspend fun timKiemGiaoDichChiTheoNgay(ngay: Int): List<GiaoDich>

    @Query("select Tien from GiaoDich where ThangGiaoDich = :thang and ThuChi = 1")
    suspend fun timKiemGiaoDichChiTheoThang(thang: Int): List<Long>

    @Query("select * from GiaoDich where NamGiaoDich = :nam and ThuChi = 1")
    suspend fun timKiemGiaoDichChiTheoNam(nam: Int): List<GiaoDich>

    @Query("select * from GiaoDich where NgayGiaoDich = :ngay and ThuChi = 0")
    suspend fun timKiemGiaoDichThuTheoNgay(ngay: Int): List<GiaoDich>

    @Query("select Tien from GiaoDich where ThangGiaoDich = :thang and ThuChi = 0")
    suspend fun timKiemGiaoDichThuTheoThang(thang: Int): List<Long>

    @Query("select * from GiaoDich where NamGiaoDich = :nam and ThuChi = 0")
    suspend fun timKiemGiaoDichThuTheoNam(nam: Int): List<GiaoDich>

    @Query("SELECT GiaoDich.Tien, DanhMuc.TenDanhMuc, DanhMuc.Icon FROM GiaoDich INNER JOIN DanhMuc ON GiaoDich.IdDanhMuc = DanhMuc.Id WHERE GiaoDich.ThangGiaoDich = :thang AND GiaoDich.ThuChi = 1")
    suspend fun timKiemGiaoDichChiBieuDo(thang: Int): List<SpendingInChart>
    @Query("SELECT GiaoDich.Tien, DanhMuc.TenDanhMuc, DanhMuc.Icon FROM GiaoDich INNER JOIN DanhMuc ON GiaoDich.IdDanhMuc = DanhMuc.Id where GiaoDich.ThangGiaoDich = :thang and GiaoDich.ThuChi = 0")
    suspend fun timKiemGiaoDichThuBieuDo(thang: Int): List<SpendingInChart>
    // Danhmucdao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun themDanhMuc(danhMuc: DanhMuc): Long
    @Query("select * from DanhMuc where Id = :id")
    suspend fun timKiemDanhMuc(id: Int): DanhMuc
    @Query("select * from DanhMuc where ThuChi = 1")
    suspend fun timKiemDanhMucChi(): List<DanhMuc>
    @Query("select * from DanhMuc where ThuChi = 0")
    suspend fun timKiemDanhMucThu(): List<DanhMuc>
}