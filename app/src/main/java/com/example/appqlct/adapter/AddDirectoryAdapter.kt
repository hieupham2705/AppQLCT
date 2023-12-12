package com.example.appqlct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlct.R
import com.example.appqlct.databinding.ItemAddDirectoryBinding
import com.example.appqlct.databinding.ItemDirectoryBinding

class AddDirectoryAdapter : RecyclerView.Adapter<AddDirectoryAdapter.ViewHolder>() {
    private val listDirectory = mutableListOf(
        R.drawable.icon_eat_and_drink,
        R.drawable.icon_daily_spending,
        R.drawable.icon_cloths,
        R.drawable.icon_cosmetics,
        R.drawable.icon_communication_fee,
        R.drawable.icon_medical,
        R.drawable.icon_education,
        R.drawable.icon_electricity_bill,
        R.drawable.icon_go,
        R.drawable.icon_bill_contact,
        R.drawable.icon_bill_home,
        R.drawable.icon_salary,
        R.drawable.icon_allowance,
        R.drawable.icon_bonus,
        R.drawable.icon_investment_money,
        R.drawable.icon_supplementary_income,
        R.drawable.icon_coc,
        R.drawable.icon_cuon,
        R.drawable.icon_gau,
        R.drawable.icon_heart,
        R.drawable.icon_note,
        R.drawable.icon_setting,
        R.drawable.icon_1,
        R.drawable.icon_2,
        R.drawable.icon_3,
        R.drawable.icon_4,
        R.drawable.icon_5,
        R.drawable.icon_6,
        R.drawable.icon_7,
        R.drawable.icon_8,
    )
    private var item = 0

    class ViewHolder(val binding: ItemAddDirectoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddDirectoryAdapter.ViewHolder {
        val binding =
            ItemAddDirectoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddDirectoryAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            imvDirectory.setImageResource(listDirectory[position])
            root.setOnClickListener {
                val i = position
                item = i
                notifyDataSetChanged()
            }
            if (position == item) {
                root.setBackgroundResource(R.drawable.border_btn_tienthu)
            } else
                root.setBackgroundResource(R.drawable.border_directory)
        }

    }


    override fun getItemCount(): Int = listDirectory.size
    fun getIcon(): Int {
        return listDirectory[item]
    }
}