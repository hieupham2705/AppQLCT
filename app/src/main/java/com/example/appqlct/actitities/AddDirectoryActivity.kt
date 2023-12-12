package com.example.appqlct.actitities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.appqlct.adapter.AddDirectoryAdapter
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.ActivityAddDirectoryBinding
import com.example.appqlct.extension.convertDrawableToBase64
import com.example.appqlct.model.DanhMuc
import kotlinx.coroutines.launch

class AddDirectoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddDirectoryBinding.inflate(layoutInflater) }
    private val adapter by lazy { AddDirectoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            recyclerview.adapter = adapter
            imbtnBack.setOnClickListener {
                finish()
            }
            btnSave.setOnClickListener {
                if (!edtName.editText?.text.toString().isEmpty()) {
                    try {
                        lifecycleScope.launch {
                            DataBaseManager.getInstance(applicationContext).getItemDAO().themDanhMuc(
                                DanhMuc(
                                    tenDanhMuc = edtName.editText?.text.toString(),
                                    icon = convertDrawableToBase64(applicationContext,adapter.getIcon()),
                                    thuChi = intent.getBooleanExtra("thuChi", true)
                                )
                            )
                            Toast.makeText(
                                applicationContext, "Đã lưu!", Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }catch (e:Exception){
                        e.message
                    }
                } else {
                    Toast.makeText(
                        applicationContext, "Vui lòng chọn tên danh mục và ảnh!", Toast.LENGTH_SHORT
                    ).show()
                    edtName.editText?.requestFocus()
                }
            }
        }
    }

}