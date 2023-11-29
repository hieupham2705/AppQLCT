package com.example.appqlct.fragment

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.appqlct.R
import com.example.appqlct.adapter.CalendarAdapter
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.FragmentCalendarBinding
import com.example.appqlct.model.Revenue
import com.example.appqlct.model.Spending
import com.example.appqlct.model.SpendingInCalendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Year

private const val TAG = "CalendarFragment"

class CalendarFragment : Fragment() {
    private val binding by lazy { FragmentCalendarBinding.inflate(layoutInflater) }
    private val listSpending = mutableListOf<Spending>()
    private val listRevenue = mutableListOf<Revenue>()
    private val listMoneySpendingInMonth = mutableListOf<Long>()
    private val listMoneyRevenueInMonth = mutableListOf<Long>()
    private val listAdapter = mutableListOf<SpendingInCalendar>()
    private val adapter by lazy { CalendarAdapter() }
    private val calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.calendarView2.setOnDateChangeListener { calendarView, i, i1, i2 ->
            Log.e(TAG, "onCreateView: $i1", )
            calendar.set(i, i1, i2)
            getData(i, i1 + 1, i2)
        }
        binding.recyclerview.adapter = adapter
        return binding.root
    }

    override fun onStart() {
        getData(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        super.onStart()
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
        CoroutineScope(Dispatchers.Main).launch {
            listSpending.clear()
            listRevenue.clear()
            listMoneySpendingInMonth.clear()
            listMoneyRevenueInMonth.clear()
            listSpending.addAll(
                DataBaseManager.getInstance(requireContext()).getItemSpendingDAO()
                    .search("${day}/${month}/${year}")
            )
            listRevenue.addAll(
                DataBaseManager.getInstance(requireContext()).getItemRevenueDAO()
                    .search("${day}/${month}/${year}")
            )
            listMoneySpendingInMonth.addAll(
                DataBaseManager.getInstance(requireContext()).getItemSpendingDAO()
                    .searchMoneySpending(month)
            )
            listMoneyRevenueInMonth.addAll(
                DataBaseManager.getInstance(requireContext()).getItemRevenueDAO()
                    .searchMoneyRevenue(month)
            )
            Log.e(TAG, "getData: $month", )
            getList()
            adapter.setAdapter("${day}/${month}/${year}", listAdapter)
            Log.e(TAG, "onCreateView: ${listAdapter}")

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
            var spendingInCalendar = SpendingInCalendar(0, it.directory, it.money, true)
            listAdapter.add(spendingInCalendar)
        }
        listRevenue.forEach {
            var spendingInCalendar = SpendingInCalendar(0, it.directory, it.money, false)
            listAdapter.add(spendingInCalendar)
        }
    }

}