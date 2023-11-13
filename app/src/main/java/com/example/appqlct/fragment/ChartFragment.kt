package com.example.appqlct.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.FragmentChartBinding
import com.example.appqlct.model.Revenue
import com.example.appqlct.model.Spending
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class ChartFragment : Fragment() {
    private val binding by lazy { FragmentChartBinding.inflate(layoutInflater) }
    private val listChart = mutableListOf<PieEntry>()
    private val _stateSpening = MutableLiveData<List<Spending>>()
    val stateSpending : LiveData<List<Spending>> = _stateSpening
    private val _stateRevenue = MutableLiveData<List<Revenue>>()
    val stateRevenue : LiveData<List<Revenue>> = _stateRevenue
    private var calender = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listChart.add(PieEntry(60f, "hasda"))
        listChart.add(PieEntry(20f))
        listChart.add(PieEntry(20f))
        var pieDataSet = PieDataSet(listChart, "")
        pieDataSet.apply {
            colors = ColorTemplate.MATERIAL_COLORS.toMutableList()
            valueTextColor = Color.BLACK
            valueTextSize = 24f
        }
        val pieData = PieData(pieDataSet)
        binding.pieChart.apply {
            isDrawEntryLabelsEnabled
            data = pieData
            animation
        }
        spending(calender.get(Calendar.MONTH))
        Revenue(calender.get(Calendar.MONTH))
        stateSpending.observe(viewLifecycleOwner){

        }
        stateRevenue.observe(viewLifecycleOwner){

        }
        binding.tapLayout.addTab(binding.tapLayout.newTab().setText("Chi tiêu"))
        binding.tapLayout.addTab(binding.tapLayout.newTab().setText("Thu nhập"))
        binding.tapLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                
            }

        })
        return binding.root
    }
    private fun spending(month : Int){
        CoroutineScope(Dispatchers.Main).launch {
            _stateSpening.value = DataBaseManager.getInstance(requireContext()).getItemSpendingDAO().searchSpending(month)
        }
    }

    private fun revenue(month : Int){
        CoroutineScope(Dispatchers.Main).launch {
            _stateRevenue.value = DataBaseManager.getInstance(requireContext()).getItemRevenueDAO().searchRevenue(month)
        }
    }

}