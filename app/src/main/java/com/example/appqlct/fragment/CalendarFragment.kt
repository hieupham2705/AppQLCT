package com.example.appqlct.fragment

import android.app.AlertDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.appqlct.actitities.EditSpendingActivity
import com.example.appqlct.adapter.CalendarAdapter
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.FragmentCalendarBinding
import com.example.appqlct.extension.showToast
import com.example.appqlct.model.GiaoDich
import com.example.appqlct.model.SpendingInCalendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "CalendarFragment"

class CalendarFragment : Fragment() {
    private val binding by lazy { FragmentCalendarBinding.inflate(layoutInflater) }
    private val listSpending = mutableListOf<GiaoDich>()
    private val listMoneySpendingInMonth = mutableListOf<Long>()
    private val listMoneyRevenueInMonth = mutableListOf<Long>()
    private val listAdapter = mutableListOf<SpendingInCalendar>()
    private val adapter by lazy {
        CalendarAdapter(
            ::onClickDelete,
            ::onClickEdit,
            requireContext()
        )
    }


    private val calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.calendarView2.setOnDateChangeListener { calendarView, i, i1, i2 ->
            Log.e(TAG, "onCreateView: $i1")
            calendar.set(i, i1, i2)
            getData(i, i1 + 1, i2)
        }
        binding.recyclerview.adapter = adapter
        return binding.root
    }

    override fun onResume() {
        getData(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        super.onResume()
    }

    private fun getData(year: Int, month: Int, day: Int) {
        lifecycleScope.launch {
            listSpending.clear()
            listMoneySpendingInMonth.clear()
            listMoneyRevenueInMonth.clear()
            listSpending.addAll(
                DataBaseManager.getInstance(requireContext()).getItemDAO()
                    .timKiemGiaoDichChiTheoNgayThangNam(day, month, year)
            )
            listMoneySpendingInMonth.addAll(
                DataBaseManager.getInstance(requireContext()).getItemDAO()
                    .timKiemGiaoDichChiTheoThang(month)
            )
            listMoneyRevenueInMonth.addAll(
                DataBaseManager.getInstance(requireContext()).getItemDAO()
                    .timKiemGiaoDichThuTheoThang(month)
            )
            Log.e(TAG, "getData: $month")
            getList()
            adapter.setAdapter("${day}/${month}/${year}", listAdapter)
            Log.e(TAG, "onCreateView: ${listMoneyRevenueInMonth}")

            val totalSpending = listMoneySpendingInMonth.fold(0) { a, b ->
                (a + b).toInt()
            }
            binding.tvSpendingMoney.text = totalSpending.toString() + " đ"
            val totalRevenue = listMoneyRevenueInMonth.fold(0) { a, b ->
                (a + b).toInt()
            }
            binding.tvRevenue.text = totalRevenue.toString() + " đ"
            binding.tvTotal.text = (totalRevenue - totalSpending).toString() + " đ"
        }
    }

    private fun getList() {
        listAdapter.clear()
        listSpending.forEach {
            var spendingInCalendar =
                SpendingInCalendar(0, it.idDanhMuc, it.Id, it.tien, it.ghiChu, it.thuChi)
            listAdapter.add(spendingInCalendar)
        }
    }

    private fun onClickDelete() {
        showYesNoDialog()
    }

    private fun onClickEdit(id: Int) {
        val intent = Intent(requireContext(), EditSpendingActivity::class.java)
        intent.putExtra("id",id)
        startActivity(intent)
    }

    private fun showYesNoDialog() {
        val builder = AlertDialog.Builder(context)

        // Thiết lập tiêu đề và nội dung của dialog
        builder.setTitle("Câu hỏi")
            .setMessage(
                "Bạn có chắc chắn muốn xóa mục? Thao tác này không thể\n" +
                        "hoàn tác lại."
            )

        // Thiết lập nút Yes
        builder.setPositiveButton("Yes") { dialog, which ->
            lifecycleScope.launch {
                DataBaseManager.getInstance(requireContext()).getItemDAO().xoaGiaoDich(
                    id = adapter.getIdGiaoDich()
                        .toLong()
                )
                getData(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH)
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
