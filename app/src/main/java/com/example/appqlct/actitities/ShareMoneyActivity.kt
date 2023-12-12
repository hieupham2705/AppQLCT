package com.example.appqlct.actitities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import com.example.appqlct.R
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.ActivityShareMoneyBinding
import com.example.appqlct.databinding.DialogAddMemberBinding
import com.example.appqlct.define.Constants
import com.example.appqlct.model.NguoiDung
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ShareMoneyActivity"
class ShareMoneyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityShareMoneyBinding.inflate(layoutInflater) }
//    private val adapter by lazy { AdapterShareMoney }
    private val listMember = mutableListOf<NguoiDung>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onClick()
    }

    private fun onClick() {
        binding.apply {
            btnAddMember.setOnClickListener {
                onClickAddMember()
            }
            recyclerview.adapter
        }
    }

    private fun onClickAddMember() {
        val hhe = Dialog(this)
        hhe.let {
            val binding = DialogAddMemberBinding.inflate(layoutInflater)
            it.setContentView(binding.root)
            it.window?.apply {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawable(ColorDrawable(Color.WHITE))
                attributes.apply {
                    y  = -170
                    gravity = Gravity.BOTTOM

                }
            }
            binding.apply {
                btnBack.setOnClickListener { view ->
                    it.dismiss()
                }
                btnAddMember.setOnClickListener {
                    if (edtName.editText?.text.toString().isEmpty()&&edtSpending.editText?.text.toString().isEmpty())
                        Constants.showToast("Vui lòng nhập đầy đủ thông tin",applicationContext)
                    else {
                        val name = edtName.editText?.text.toString()
                        try {
                            val spending = edtSpending.editText?.text.toString().toLong()
                            val nguoiDung = NguoiDung(ten = name, khoanChi = spending)
                            listMember.add(nguoiDung)
                        }catch (e:Exception){
                            Log.e(TAG, "onClickAddMember: ${e.message}")
                        }

                    }
                }
            }
            it.setCancelable(true)
            it.show()
        }

    }
}