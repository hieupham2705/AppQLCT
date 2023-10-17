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

private const val TAG = "CalendarFragment"

class CalendarFragment : Fragment() {
    private val binding by lazy { FragmentCalendarBinding.inflate(layoutInflater) }
    private var dateChosse: String = ""
    private val listSpending = mutableListOf<Spending>()
    private val listRevenue = mutableListOf<Revenue>()
    private val listAdapter = mutableListOf<SpendingInCalendar>()
    private val adapter by lazy { CalendarAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.calendarView2.setOnDateChangeListener { calendarView, i, i2, i3 ->
            dateChosse = "${i3}/${i2 + 1}/${i}"
            listSpending.clear()
            listRevenue.clear()
            CoroutineScope(Dispatchers.Main).launch {
                listSpending.addAll(
                    DataBaseManager.getInstance(requireContext()).getItemSpendingDAO()
                        .search(dateChosse)
                )
                CoroutineScope(Dispatchers.Main).launch {
                    listRevenue.addAll(
                        DataBaseManager.getInstance(requireContext()).getItemRevenueDAO()
                            .search(dateChosse)
                    )
                    getList()
                    adapter.setAdapter(dateChosse, listAdapter)
                    Log.e(TAG, "onCreateView: ${listAdapter}")
                }
            }

            Log.e(TAG, "onCreateView: ${dateChosse}")
        }
        binding.recyclerview.adapter = adapter
        return binding.root
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