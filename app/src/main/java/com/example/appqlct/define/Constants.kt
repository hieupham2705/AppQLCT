package com.example.appqlct.define

import android.content.Context
import android.widget.Toast

object Constants {
    const val ENTER_USER_NAME = "Email trống"
    const val TIEN_THU = "Tiền thu"
    const val TIEN_CHI = "Tiền chi"
    const val BTN_TIEN_CHI = "Nhập khoản tiền chi"
    const val BTN_TIEN_THU = "Nhập khoản tiền thu"
    const val ADD_SUCCESSFUL = "Thêm thành công"
    const val PLS_ENTER_THE_AMOUNT = "Vui lòng nhâp số tiền"

    fun showToast(string: String?, context: Context?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }
}