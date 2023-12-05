package com.example.appqlct.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appqlct.R
import com.example.appqlct.dao.Daoo
import com.example.appqlct.extension.convertDrawableToBase64
import com.example.appqlct.model.DanhMuc
import com.example.appqlct.model.Directory
import com.example.appqlct.model.GiaoDich
import com.example.appqlct.model.HoaDon
import com.example.appqlct.model.NguoiDung
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [DanhMuc::class, GiaoDich::class, HoaDon::class, NguoiDung::class],
    version = 2,
    exportSchema = false
)
abstract class DataBaseManager : RoomDatabase() {
    abstract fun getItemDAO(): Daoo

//    companion object {
//        @Volatile
//        private var INSTANCE: DataBaseManager? = null
//
//        fun getInstance(context: Context): DataBaseManager {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    DataBaseManager::class.java,
//                    "adasdqwdasdasdadwq"
//                ).addCallback(object : RoomDatabase.Callback() {
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                        super.onCreate(db)
//                        Log.e("afwedqsdasdqwd", "onCreate: ", )
//                        GlobalScope.launch {
//                            populateDatabase(tempInstance?.getItemDAO()!!,context)
//                        }
//                    }
//                }).fallbackToDestructiveMigration().build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//
//        private suspend fun populateDatabase(phongDao: Daoo, context: Context) {
//            phongDao?.themDanhMuc(
//                DanhMuc(
//                    1,
//                    convertDrawableToBase64(context, R.drawable.icon_eat_and_drink)
//                )
//            )
//            phongDao?.themDanhMuc(
//                DanhMuc(
//                    2,
//                    convertDrawableToBase64(context, R.drawable.icon_eat_and_drink)
//                )
//            )
//            phongDao?.themDanhMuc(
//                DanhMuc(
//                    3,
//                    convertDrawableToBase64(context, R.drawable.icon_eat_and_drink)
//                )
//            )
//            phongDao?.themDanhMuc(
//                DanhMuc(
//                    4,
//                    convertDrawableToBase64(context, R.drawable.icon_eat_and_drink)
//                )
//            )
//            phongDao?.themDanhMuc(
//                DanhMuc(
//                    5,
//                    convertDrawableToBase64(context, R.drawable.icon_eat_and_drink)
//                )
//            )
//            phongDao?.themDanhMuc(
//                DanhMuc(
//                    6,
//                    convertDrawableToBase64(context, R.drawable.icon_eat_and_drink)
//                )
//            )
//            phongDao?.themDanhMuc(
//                DanhMuc(
//                    7,
//                    convertDrawableToBase64(context, R.drawable.icon_eat_and_drink)
//                )
//            )
//            phongDao?.themDanhMuc(
//                DanhMuc(
//                    8,
//                    convertDrawableToBase64(context, R.drawable.icon_eat_and_drink)
//                )
//            )
//        }
//
//    }
companion object {
    private const val DATABASE_NAME = "data"

    @Volatile
    private var instance: DataBaseManager? = null

    fun getInstance(context: Context): DataBaseManager {
        return instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }

    private fun buildDatabase(context: Context): DataBaseManager {
        return Room.databaseBuilder(context, DataBaseManager::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    GlobalScope.launch {
                        prepopulateDatabase(instance?.getItemDAO(),context)
                    }
                }
            }
            )
            .build()
    }

    private suspend fun prepopulateDatabase(dao: Daoo?,context: Context) {
        dao?.themDanhMuc(DanhMuc(1, convertDrawableToBase64(context,R.drawable.icon_eat_and_drink),"Ăn uống",true))
        dao?.themDanhMuc(DanhMuc(2, convertDrawableToBase64(context,R.drawable.icon_daily_spending),"Chi tiêu hàng ngày",true))
        dao?.themDanhMuc(DanhMuc(3, convertDrawableToBase64(context,R.drawable.icon_cloths),"Quần áo",true))
        dao?.themDanhMuc(DanhMuc(4, convertDrawableToBase64(context,R.drawable.icon_cosmetics),"Mỹ phẩm",true))
        dao?.themDanhMuc(DanhMuc(5, convertDrawableToBase64(context,R.drawable.icon_communication_fee),"Phí giao lưu",true))
        dao?.themDanhMuc(DanhMuc(6, convertDrawableToBase64(context,R.drawable.icon_medical),"Y tế",true))
        dao?.themDanhMuc(DanhMuc(7, convertDrawableToBase64(context,R.drawable.icon_education),"Giáo dục",true))
        dao?.themDanhMuc(DanhMuc(8, convertDrawableToBase64(context,R.drawable.icon_electricity_bill),"Tiền điện",true))
        dao?.themDanhMuc(DanhMuc(9, convertDrawableToBase64(context,R.drawable.icon_go),"Đi lại",true))
        dao?.themDanhMuc(DanhMuc(10, convertDrawableToBase64(context,R.drawable.icon_bill_contact),"Phí liên lạc",true))
        dao?.themDanhMuc(DanhMuc(11, convertDrawableToBase64(context,R.drawable.icon_bill_home),"Tiền nhà",true))
        dao?.themDanhMuc(DanhMuc(21, convertDrawableToBase64(context,R.drawable.icon_salary),"Tiền lương",false))
        dao?.themDanhMuc(DanhMuc(22, convertDrawableToBase64(context,R.drawable.icon_allowance),"Tiền phụ cấp",false))
        dao?.themDanhMuc(DanhMuc(23, convertDrawableToBase64(context,R.drawable.icon_bonus),"Tiền thưởng",false))
        dao?.themDanhMuc(DanhMuc(24, convertDrawableToBase64(context,R.drawable.icon_investment_money),"Đầu tư",false))
        dao?.themDanhMuc(DanhMuc(25, convertDrawableToBase64(context,R.drawable.icon_supplementary_income),"Thu nhập phụ",false))
    }
}
}