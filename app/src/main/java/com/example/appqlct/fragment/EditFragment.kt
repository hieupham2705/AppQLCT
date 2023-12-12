package com.example.appqlct.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.appqlct.R
import com.example.appqlct.actitities.EditDirectoryActivity
import com.example.appqlct.adapter.DirectoryAdapter
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.FragmentEditBinding
import com.example.appqlct.define.Constants
import com.example.appqlct.extension.convertDrawableToBase64
import com.example.appqlct.extension.showToast
import com.example.appqlct.model.DanhMuc
import com.example.appqlct.model.Directory
import com.example.appqlct.model.GiaoDich
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

private const val TAG = "EditFragment"

class EditFragment : Fragment() {

    private val binding by lazy { FragmentEditBinding.inflate(layoutInflater) }
    private val listDirectorySpendingMoney = mutableListOf<DanhMuc>()
    private val listDirectoryRevenue = mutableListOf<DanhMuc>()
    private val adapter by lazy {
        DirectoryAdapter(
            onCickEditDirectory = {
                onCickEditDirectory()
            }
        )
    }

    private fun onCickEditDirectory() {
        startActivity(Intent(requireContext(), EditDirectoryActivity::class.java))
    }

    private var hieu = true
    var calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            recyclerview.adapter = adapter
            btnAccept.setOnClickListener {
                if (edtSpendingMoney.editText?.text.toString().isEmpty())
                    showToast(Constants.PLS_ENTER_THE_AMOUNT)
                else
                    updateSpending()
            }
            imvAccept.setOnClickListener {
                if (edtSpendingMoney.editText?.text.toString().isEmpty())
                    showToast(Constants.PLS_ENTER_THE_AMOUNT)
                else
                    updateSpending()
            }
            btnTienChi.setOnClickListener {
                hieu = true
                onClickTienChi()
            }
            btnTienThu.setOnClickListener {
                hieu = false
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
        }
        return binding.root
    }

    private fun onClickday() {
        val datePicker = DatePickerDialog(
            requireContext(), { view, yearPicker, monthPicker, dayOfMonthPicker ->
                // Xử lý khi người dùng chọn ngày
                calendar.set(Calendar.YEAR, yearPicker)
                calendar.set(Calendar.MONTH + 1, monthPicker)
                Log.e(TAG, "onClickday: $monthPicker")
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonthPicker)
                val selectedDate =
                    "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${
                        calendar.get(Calendar.YEAR)
                    }" // Month is zero-based
                binding.tvDay.text = selectedDate
            },
            year, month, dayOfMonth
        )
        datePicker.show()
    }

    private fun updateSpending() {
        binding.apply {
            val giaoDich = GiaoDich(
                ngayGiaoDich = calendar.get(Calendar.DAY_OF_MONTH),
                thangGiaoDich = calendar.get(Calendar.MONTH) + 1,
                namGiaoDich = calendar.get(Calendar.YEAR),
                tien = edtSpendingMoney.editText?.text.toString().toLong(),
                ghiChu = edtNote.editText?.text.toString(),
                thuChi = hieu
            )
            if (hieu)
                giaoDich.idDanhMuc = listDirectorySpendingMoney[adapter.getIndex()].Id
            else
                giaoDich.idDanhMuc = listDirectoryRevenue[adapter.getIndex()].Id
            lifecycleScope.launch {
                DataBaseManager.getInstance(requireContext()).getItemDAO()
                    .themNguoiGiaoDich(giaoDich)

                showToast(Constants.ADD_SUCCESSFUL)
            }
            edtNote.editText?.text = null
            edtSpendingMoney.editText?.text = null
        }
    }

    private fun onClickTienThu() {
        getDanhMucThu()
        binding.apply {
            btnTienThu.setBackgroundResource(R.color.pink)
            btnTienThu.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            btnTienChi.setBackgroundResource(R.drawable.border_btn_tienthu)
            btnTienChi.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            tvSpendingMoneyOrRevenue.text = Constants.TIEN_THU
            btnAccept.text = Constants.BTN_TIEN_THU
            adapter.setAdapter(listDirectoryRevenue)
        }
    }

    private fun onClickTienChi() {
        getDanhMucChi()
        binding.apply {
            btnTienChi.setBackgroundResource(R.color.pink)
            btnTienChi.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            btnTienThu.setBackgroundResource(R.drawable.border_btn_tienthu)
            btnTienThu.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            tvSpendingMoneyOrRevenue.text = Constants.TIEN_CHI
            btnAccept.text = Constants.BTN_TIEN_CHI
        }
    }

    override fun onStart() {
        super.onStart()
        binding.tvDay.text = "$dayOfMonth/${month + 1}/$year"
        Log.e(TAG, "onDestroyView: hihi")
    }

    private fun getDanhMucChi() {
        lifecycleScope.launch {
            listDirectorySpendingMoney.clear()
            listDirectorySpendingMoney.addAll(
                DataBaseManager.getInstance(requireContext()).getItemDAO().timKiemDanhMucChi()
            )
            adapter.setAdapter(listDirectorySpendingMoney)
        }
    }

    private fun getDanhMucThu() {
        lifecycleScope.launch {
            listDirectoryRevenue.clear()
            listDirectoryRevenue.addAll(
                DataBaseManager.getInstance(requireContext()).getItemDAO().timKiemDanhMucThu()
            )
            adapter.setAdapter(listDirectoryRevenue)
        }
    }

    override fun onResume() {
        super.onResume()
        if (hieu)
            getDanhMucChi()
        else
            getDanhMucThu()
    }
}