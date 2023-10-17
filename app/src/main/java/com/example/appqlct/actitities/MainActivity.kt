package com.example.appqlct.actitities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.appqlct.R
import com.example.appqlct.adapter.ViewPagerAdapter
import com.example.appqlct.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.meowbottomnavigation.add(MeowBottomNavigation.Model(0, R.drawable.pencil))
        binding.meowbottomnavigation.add(
            MeowBottomNavigation.Model(
                1,
                R.drawable.lich
            )
        )
        binding.meowbottomnavigation.add(
            MeowBottomNavigation.Model(
                2,
                R.drawable.bao_cao_icon
            )
        )
        binding.meowbottomnavigation.add(
            MeowBottomNavigation.Model(
                3,
                R.drawable.menu_icon
            )
        )
        meownavigation()
    }

    private fun meownavigation() {
        val adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        binding.viewpager.adapter = adapter
        binding.meowbottomnavigation.setOnShowListener {
            binding.viewpager.setCurrentItem(it.id)
        }
        binding.meowbottomnavigation.setOnReselectListener { }
        binding.meowbottomnavigation.setOnClickMenuListener {

        }
        binding.meowbottomnavigation.show(0, true)
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.meowbottomnavigation.show(position,true)
            }
        })
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.viewpager, fragment, "").commit()
    }
}
