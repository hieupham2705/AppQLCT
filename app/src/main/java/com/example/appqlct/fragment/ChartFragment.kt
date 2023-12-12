package com.example.appqlct.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.anychart.anychart.AnyChart
import com.anychart.anychart.DataEntry
import com.anychart.anychart.ValueDataEntry
import com.example.appqlct.adapter.ChartAdapter
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.FragmentChartBinding
import com.example.appqlct.model.SpendingInChart
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

private const val TAG = "ChartFragment"

class ChartFragment : Fragment() {
    private val binding by lazy { FragmentChartBinding.inflate(layoutInflater) }
    private val adapter by lazy { ChartAdapter() }
    private var calendar = Calendar.getInstance()
    private var rv = 0
    private var spd = 0
    private val pie = AnyChart.pie()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.recyclerview.adapter = adapter
        binding.tapLayout.addTab(binding.tapLayout.newTab().setText("Chi tiêu"))
        binding.tapLayout.addTab(binding.tapLayout.newTab().setText("Thu nhập"))
        createDataChart()
        binding.tvMonth.text = "Tháng " + (calendar.get(Calendar.MONTH) + 1).toString()
        binding.tapLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        spendingAdapter()
                    }

                    else -> {
                        revenueAdapter()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        spendingAdapter()
                    }

                    else -> {
                        revenueAdapter()
                    }
                }
            }

        })
        binding.apply {
            imvBackNonth.setOnClickListener {
                calendar.add(Calendar.MONTH, -1)
                val selectedDate =
                    "${calendar.get(Calendar.MONTH) + 1}"
                tvMonth.text = "Tháng " + selectedDate
                check()
            }
            imvIncreaseMonth.setOnClickListener {
                calendar.add(Calendar.MONTH, 1)
                val selectedDate =
                    "${calendar.get(Calendar.MONTH) + 1}"
                tvMonth.text = "Tháng " + selectedDate
                check()
            }
        }
        return binding.root
    }

    private fun spendingAdapter() {
        lifecycleScope.launch {
            val dataEntries = mutableListOf<DataEntry>()
            val spendingInChart = mutableListOf<SpendingInChart>()
            spd = 0
            DataBaseManager.getInstance(requireContext()).getItemDAO()
                .timKiemGiaoDichChiBieuDo(calendar.get(Calendar.MONTH) + 1).groupBy { it.TenDanhMuc }
                ?.forEach { (_directory, _spendings) ->
                    val s = SpendingInChart(
                        _spendings.sumByDouble { it.Tien!!.toDouble() }.toLong(),
                        _directory,
                        _spendings.get(0).Icon
                    )
                    spendingInChart.add(s)
                    dataEntries.add(ValueDataEntry(s.TenDanhMuc, s.Tien))
                    spd -= s.Tien!!.toInt()
                }
            updateTotal()
            if (dataEntries.isEmpty()) {
                binding.anyChart.isGone = true
                binding.tvNothing.isVisible = true
            } else {
                pie.setData(dataEntries)
                binding.anyChart.isVisible = true
                binding.tvNothing.isGone = true
            }
            adapter.setAdapter(spendingInChart)
        }
    }

    private fun revenueAdapter() {
        lifecycleScope.launch {
            val dataEntries = mutableListOf<DataEntry>()
            val spendingInChart = mutableListOf<SpendingInChart>()
            rv = 0
            DataBaseManager.getInstance(requireContext()).getItemDAO()
                .timKiemGiaoDichThuBieuDo(calendar.get(Calendar.MONTH) + 1).groupBy { it.TenDanhMuc }
                ?.forEach { (_directory, _spendings) ->
                    val s = SpendingInChart(
                        _spendings.sumByDouble { it.Tien!!.toDouble() }.toLong(),
                        _directory,
                        _spendings.get(0).Icon
                    )
                    spendingInChart.add(s)
                    dataEntries.add(ValueDataEntry(s.TenDanhMuc, s.Tien))
                    rv += s.Tien!!.toInt()
                }
            updateTotal()
            if (dataEntries.isEmpty()) {
                binding.anyChart.isGone = true
                binding.tvNothing.isVisible = true
            } else {
                pie.setData(dataEntries)
                binding.anyChart.isVisible = true
                binding.tvNothing.isGone = true
            }
            adapter.setAdapter(spendingInChart)
        }
    }

    override fun onResume() {
        check()
        super.onResume()
    }

    private fun createDataChart() {
        revenueAdapter()
        spendingAdapter()
        binding.anyChart.setChart(pie)
    }

    private fun check() {
        when (binding.tapLayout.selectedTabPosition) {
            0 -> {
                revenueAdapter()
                spendingAdapter()
            }

            else -> {
                spendingAdapter()
                revenueAdapter()
            }
        }
    }

    private fun updateTotal() {
        binding.tvSpending.text = spd.toString() + " đ"
        binding.tvRevenue.text = rv.toString() + " đ"
        binding.tvTotal.text = (spd + rv).toString() + " đ"
    }
}