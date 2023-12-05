package com.example.appqlct.actitities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.appqlct.R
import com.example.appqlct.adapter.EditDirectoryAdapter
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.ActivityEditDirectoryBinding
import com.example.appqlct.define.Constants
import com.example.appqlct.model.DanhMuc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditDirectoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEditDirectoryBinding.inflate(layoutInflater) }
    private val adapter by lazy { EditDirectoryAdapter() }
    private val listDirectory = mutableListOf<DanhMuc>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onClickTienChi()
        binding.apply {
            recyclerview.adapter = adapter
            btnTienChi.setOnClickListener {
                onClickTienChi()

            }
            btnTienThu.setOnClickListener {
                onClickTienThu()
            }
            imbtnAdd.setOnClickListener {

            }
            imbtnBack.setOnClickListener { finish() }
        }

    }

    private fun onClickTienThu() {
        CoroutineScope(Dispatchers.Main).launch {
            listDirectory.clear()
            listDirectory.addAll(
                DataBaseManager.getInstance(applicationContext).getItemDAO()
                    .timKiemDanhMucThu()
            )
            adapter.setAdapter(listDirectory)
        }
        binding.apply {
            btnTienThu.setBackgroundResource(R.color.pink)
            btnTienThu.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            btnTienChi.setBackgroundResource(R.drawable.border_btn_tienthu)
            btnTienChi.setTextColor(ContextCompat.getColor(applicationContext, R.color.pink))
        }
    }

    private fun onClickTienChi() {
        CoroutineScope(Dispatchers.Main).launch {
            listDirectory.clear()
            listDirectory.addAll(
                DataBaseManager.getInstance(applicationContext).getItemDAO()
                    .timKiemDanhMucChi()
            )
            adapter.setAdapter(listDirectory)
        }
        binding.apply {
            btnTienChi.setBackgroundResource(R.color.pink)
            btnTienChi.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            btnTienThu.setBackgroundResource(R.drawable.border_btn_tienthu)
            btnTienThu.setTextColor(ContextCompat.getColor(applicationContext, R.color.pink))
        }
    }
}