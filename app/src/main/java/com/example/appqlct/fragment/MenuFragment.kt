package com.example.appqlct.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appqlct.R
import com.example.appqlct.actitities.ShareMoneyActivity
import com.example.appqlct.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private val binding by lazy { FragmentMenuBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onClick()
        return binding.root
    }

    private fun onClick() {
        binding.apply {
            lnShareMoney.setOnClickListener {
                startActivity(Intent(requireContext(),ShareMoneyActivity::class.java) )
            }
            lnDelete.setOnClickListener {

            }
            lnNotification.setOnClickListener {

            }
        }
    }
}