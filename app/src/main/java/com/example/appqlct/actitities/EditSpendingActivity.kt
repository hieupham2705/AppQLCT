package com.example.appqlct.actitities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.appqlct.R
import com.example.appqlct.adapter.DirectoryAdapter
import com.example.appqlct.adapter.EditSpendingDirectoryAdapter
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.ActivityEditSpendingBinding
import com.example.appqlct.define.Constants
import com.example.appqlct.define.Constants.showToast
import com.example.appqlct.extension.showToast
import com.example.appqlct.model.DanhMuc
import com.example.appqlct.model.GiaoDich
import kotlinx.coroutines.launch
import java.util.Calendar

private const val TAG = "EditSpendingActivity"

class EditSpendingActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEditSpendingBinding.inflate(layoutInflater) }
    private var giaoDich: GiaoDich = GiaoDich()
    private val listDirectorySpendingMoney = mutableListOf<DanhMuc>()
    private val listDirectoryRevenue = mutableListOf<DanhMuc>()
    private val calendar = Calendar.getInstance()
    private val adapter by lazy {
        EditSpendingDirectoryAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id = intent.getIntExtra("id", -1)
        if (id != 1) {
            lifecycleScope.launch {
                giaoDich = DataBaseManager.getInstance(applicationContext).getItemDAO()
                    .timKiemGiaoDichTheoId(id)
                giaoDich.namGiaoDich?.let { calendar.set(Calendar.YEAR, it) }
                giaoDich.thangGiaoDich?.let { calendar.set(Calendar.MONTH, it-1) }
                giaoDich.ngayGiaoDich?.let { calendar.set(Calendar.DAY_OF_MONTH, it) }
                binding.apply {
                    tvDay.text =
                        "${giaoDich.ngayGiaoDich}/${giaoDich.thangGiaoDich}/${giaoDich.namGiaoDich}"
                    edtNote.editText?.setText(giaoDich.ghiChu)
                    edtSpendingMoney.editText?.setText(giaoDich.tien.toString())
                }
            }
        }
        binding.apply {
            recyclerview.adapter = adapter
            btnAccept.setOnClickListener {
                if (edtSpendingMoney.editText?.text.toString().isEmpty())
                    showToast(Constants.PLS_ENTER_THE_AMOUNT, applicationContext)
                else
                    updateSpending()
            }
            imvAccept.setOnClickListener {
                if (edtSpendingMoney.editText?.text.toString().isEmpty())
                    showToast(Constants.PLS_ENTER_THE_AMOUNT, applicationContext)
                else
                    updateSpending()
            }
            btnTienChi.setOnClickListener {
                giaoDich.thuChi = true
                onClickTienChi()
            }
            btnTienThu.setOnClickListener {
                giaoDich.thuChi = false
                onClickTienThu()
            }
            tvDay.setOnClickListener {
                onClickday()
            }
            imvBackDay.setOnClickListener {
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                val selectedDate =
                    "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${
                        calendar.get(Calendar.YEAR)
                    }" // Month is zero-based
                tvDay.text = selectedDate
            }
            imvIncreaseDay.setOnClickListener {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                val selectedDate =
                    "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${
                        calendar.get(Calendar.YEAR)
                    }" // Month is zero-based
                tvDay.text = selectedDate
            }
            imbtnBack.setOnClickListener { finish() }
        }
    }

    private fun onClickday() {
        val datePicker = DatePickerDialog(
            this,
            { view, yearPicker, monthPicker, dayOfMonthPicker ->
                // Xử lý khi người dùng chọn ngày
                calendar.set(Calendar.YEAR, yearPicker)
                calendar.set(Calendar.MONTH, monthPicker )
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonthPicker)
                val selectedDate =
                    "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${
                        calendar.get(Calendar.YEAR)
                    }" // Month is zero-based
                binding.tvDay.text = selectedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun updateSpending() {
        binding.apply {
            giaoDich.ngayGiaoDich = calendar.get(Calendar.DAY_OF_MONTH)
            giaoDich.thangGiaoDich = calendar.get(Calendar.MONTH) + 1
            giaoDich.namGiaoDich = calendar.get(Calendar.YEAR)
            giaoDich.tien = edtSpendingMoney.editText?.text.toString().toLong()
            giaoDich.ghiChu = edtNote.editText?.text.toString()
            if (giaoDich.thuChi == true)
                giaoDich.idDanhMuc = listDirectorySpendingMoney[adapter.getIndex()].Id
            else
                giaoDich.idDanhMuc = listDirectoryRevenue[adapter.getIndex()].Id
            lifecycleScope.launch {
                DataBaseManager.getInstance(applicationContext).getItemDAO()
                    .capNhatGiaoDich(giaoDich)
                showToast(Constants.UPDATE_SUCCESSFUL, applicationContext)
                finish()
            }
        }
    }

    private fun onClickTienThu() {
        getDanhMucThu()
        binding.apply {
            btnTienThu.setBackgroundResource(R.color.pink)
            btnTienThu.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            btnTienChi.setBackgroundResource(R.drawable.border_btn_tienthu)
            btnTienChi.setTextColor(ContextCompat.getColor(applicationContext, R.color.pink))
            tvSpendingMoneyOrRevenue.text = Constants.TIEN_THU
            btnAccept.text = Constants.BTN_TIEN_THU
            adapter.setAdapter(listDirectoryRevenue)
        }
    }

    private fun onClickTienChi() {
        getDanhMucChi()
        binding.apply {
            btnTienChi.setBackgroundResource(R.color.pink)
            btnTienChi.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            btnTienThu.setBackgroundResource(R.drawable.border_btn_tienthu)
            btnTienThu.setTextColor(ContextCompat.getColor(applicationContext, R.color.pink))
            tvSpendingMoneyOrRevenue.text = Constants.TIEN_CHI
            btnAccept.text = Constants.BTN_TIEN_CHI
        }
    }

    private fun getDanhMucChi() {
        lifecycleScope.launch {
            listDirectorySpendingMoney.clear()
            listDirectorySpendingMoney.addAll(
                DataBaseManager.getInstance(applicationContext).getItemDAO().timKiemDanhMucChi()
            )
            adapter.setAdapter(listDirectorySpendingMoney)
        }
    }

    private fun getDanhMucThu() {
        lifecycleScope.launch {
            listDirectoryRevenue.clear()
            listDirectoryRevenue.addAll(
                DataBaseManager.getInstance(applicationContext).getItemDAO().timKiemDanhMucThu()
            )
            adapter.setAdapter(listDirectoryRevenue)
        }
    }

    override fun onResume() {
        super.onResume()
        if (giaoDich.thuChi == true)
            getDanhMucChi()
        else
            getDanhMucThu()
    }
}