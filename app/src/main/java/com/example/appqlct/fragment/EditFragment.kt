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
import com.example.appqlct.R
import com.example.appqlct.actitities.EditDirectoryActivity
import com.example.appqlct.adapter.DirectoryAdapter
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.FragmentEditBinding
import com.example.appqlct.define.Constants
import com.example.appqlct.extension.showToast
import com.example.appqlct.model.Directory
import com.example.appqlct.model.Revenue
import com.example.appqlct.model.Spending
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

private const val TAG = "EditFragment"

class EditFragment : Fragment() {

    private val binding by lazy { FragmentEditBinding.inflate(layoutInflater) }
    private val listDirectorySpendingMoney =
        mutableListOf<Directory>(
            Directory(R.drawable.icon_eat_and_drink, "Ăn uống"),
            Directory(R.drawable.icon_daily_spending, "Chi tiêu hàng ngày"),
            Directory(R.drawable.icon_cloths, "Quần áo"),
            Directory(R.drawable.icon_cosmetics, "Mỹ phẩm"),
            Directory(R.drawable.icon_communication_fee, "Phí giao lưu"),
            Directory(R.drawable.icon_medical, "Y tế"),
            Directory(R.drawable.icon_education, "Giáo dục"),
            Directory(R.drawable.icon_electricity_bill, "Tiền điện"),
            Directory(R.drawable.icon_go, "Đi lại"),
            Directory(R.drawable.icon_bill_contact, "Phí liên lạc"),
            Directory(R.drawable.icon_bill_home, "Tiền nhà")
        )
    private val listDirectoryRevenue =
        mutableListOf<Directory>(
            Directory(R.drawable.icon_salary, "Tiền lương"),
            Directory(R.drawable.icon_allowance, "Tiền phụ cấp"),
            Directory(R.drawable.icon_bonus, "Tiền thưởng"),
            Directory(R.drawable.icon_investment_money, "Đầu tư"),
            Directory(R.drawable.icon_supplementary_income, "Thu nhập phụ")
        )
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
        adapter.setAdapter(listDirectorySpendingMoney)
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
                Log.e(TAG, "onClickday: $monthPicker", )
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
            val directory = adapter.getDirectory()
            val spending = Spending(
                id = 0,
                tvDay.text.toString(),
                calendar.get(Calendar.MONTH)+1,
                edtNote.editText?.text.toString(),
                edtSpendingMoney.editText?.text.toString().toLong(),
                directory
            )
            val revenue = Revenue(
                id = 0,
                tvDay.text.toString(),
                calendar.get(Calendar.MONTH),
                edtNote.editText?.text.toString()+1,
                edtSpendingMoney.editText?.text.toString().toLong(),
                directory
            )
            if (hieu)
                CoroutineScope(Dispatchers.Main).launch {
                    DataBaseManager.getInstance(requireContext()).getItemSpendingDAO()
                        .insert(spending)
                    showToast(Constants.ADD_SUCCESSFUL)
                }
            else
                CoroutineScope(Dispatchers.Main).launch {
                    DataBaseManager.getInstance(requireContext()).getItemRevenueDAO()
                        .insert(revenue)
                    showToast(Constants.ADD_SUCCESSFUL)
                }
        }
    }

    private fun onClickTienThu() {
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
        binding.apply {
            btnTienChi.setBackgroundResource(R.color.pink)
            btnTienChi.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            btnTienThu.setBackgroundResource(R.drawable.border_btn_tienthu)
            btnTienThu.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            tvSpendingMoneyOrRevenue.text = Constants.TIEN_CHI
            btnAccept.text = Constants.BTN_TIEN_CHI
            adapter.setAdapter(listDirectorySpendingMoney)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.tvDay.text = "$dayOfMonth/${month + 1}/$year"
        Log.e(TAG, "onDestroyView: hihi")
    }
}