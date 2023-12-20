package com.example.appqlct.actitities

import android.app.AlertDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
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
    private val adapter by lazy { EditDirectoryAdapter(
        ::onClickEdit,
        ::onClickDelete,
        applicationContext
    ) }



    private val listDirectory = mutableListOf<DanhMuc>()
    private var thuCHi = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onClickTienChi()
        binding.apply {
            recyclerview.adapter = adapter
            btnTienChi.setOnClickListener {
                onClickTienChi()
                getDataCHi()
                thuCHi = true
            }
            btnTienThu.setOnClickListener {
                onClickTienThu()
                getDataThu()
                thuCHi = false
            }
            imbtnAdd.setOnClickListener {
                val intent = Intent(applicationContext, AddDirectoryActivity::class.java)
                intent.putExtra("thuChi", thuCHi)
                startActivity(intent)
            }
            imbtnBack.setOnClickListener { finish() }
        }

    }

    private fun onClickTienThu() {
        binding.apply {
            btnTienThu.setBackgroundResource(R.color.pink)
            btnTienThu.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            btnTienChi.setBackgroundResource(R.drawable.border_btn_tienthu)
            btnTienChi.setTextColor(ContextCompat.getColor(applicationContext, R.color.pink))

        }
    }

    private fun onClickTienChi() {
        binding.apply {
            btnTienChi.setBackgroundResource(R.color.pink)
            btnTienChi.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            btnTienThu.setBackgroundResource(R.drawable.border_btn_tienthu)
            btnTienThu.setTextColor(ContextCompat.getColor(applicationContext, R.color.pink))
        }
    }

    override fun onStart() {
        super.onStart()
        if (thuCHi) {
            getDataCHi()
        } else {
            getDataThu()
        }
    }

    private fun getDataThu() {
        try {
            lifecycleScope.launch {
                listDirectory.clear()
                listDirectory.addAll(
                    DataBaseManager.getInstance(applicationContext).getItemDAO()
                        .timKiemDanhMucThu()
                )
                adapter.setAdapter(listDirectory)
            }
        } catch (e: Exception) {
            e.message
        }
    }

    private fun getDataCHi() {
        try {
            lifecycleScope.launch {
                listDirectory.clear()
                listDirectory.addAll(
                    DataBaseManager.getInstance(applicationContext).getItemDAO()
                        .timKiemDanhMucChi()
                )
                adapter.setAdapter(listDirectory)
            }
        } catch (e: Exception) {
            e.message
        }
    }
    private fun onClickDelete() {
        showYesNoDialog()
    }
    private fun onClickEdit() {
        val intent = Intent(this,AddDirectoryActivity::class.java)
        intent.putExtra("name",adapter.getDanhMuc().tenDanhMuc)
        intent.putExtra("id",adapter.getDanhMuc().Id)
        startActivity(intent)
    }
    private fun showYesNoDialog()  {
        val builder = AlertDialog.Builder(this)

        // Thiết lập tiêu đề và nội dung của dialog
        builder.setTitle("Câu hỏi")
            .setMessage(
                "Bạn có chắc chắn muốn xóa danh mục này? Thao tác này không thể\n" +
                        "hoàn tác lại."
            )

        // Thiết lập nút Yes
        builder.setPositiveButton("Yes") { dialog, which ->
            lifecycleScope.launch {
                DataBaseManager.getInstance(applicationContext).getItemDAO().xoaDanhMuc(
                    id = adapter.getDanhMuc().Id
                        .toLong()
                )
            }

            dialog.dismiss()
        }

        // Thiết lập nút No
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        // Hiển thị dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}