package com.example.appqlct.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appqlct.fragment.CalendarFragment
import com.example.appqlct.fragment.ChartFragment
import com.example.appqlct.fragment.EditFragment
import com.example.appqlct.fragment.MenuFragment

private const val TAG = "ViewPagerAdapter"
class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val i = position
        return when(i){
            0 -> EditFragment()
            1 -> CalendarFragment()
            2 -> ChartFragment()
            3 -> MenuFragment()
            else -> EditFragment()
        }
    }

}