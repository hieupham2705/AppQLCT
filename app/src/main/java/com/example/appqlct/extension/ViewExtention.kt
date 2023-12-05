package com.example.appqlct.extension

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appqlct.R
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

fun View.hideKeyboard(){
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}
fun Fragment.showToast(mess: String) {
    Toast.makeText(context,mess,Toast.LENGTH_SHORT).show()
}
fun decodeBase64ToBitmap(base64String: String): Bitmap? {
    val imageBytes: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
}
fun convertDrawableToBase64(context: Context, drawableResId: Int): String? {
    try {
        // Chuyển đổi drawable thành Bitmap
        val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, drawableResId)
        // Chuyển đổi Bitmap thành mảng byte
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val imageBytes: ByteArray = baos.toByteArray()

        // Chuyển đổi mảng byte thành chuỗi Base64
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}