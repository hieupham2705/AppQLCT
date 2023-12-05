package com.example.appqlct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlct.databinding.ItemSpendingBinding
import com.example.appqlct.extension.decodeBase64ToBitmap
import com.example.appqlct.model.SpendingInChart

class ChartAdapter : RecyclerView.Adapter<ChartAdapter.ViewHoder>() {

    private val listAdapter = mutableListOf<SpendingInChart>()

    class ViewHoder(val binding: ItemSpendingBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartAdapter.ViewHoder {
        val binding =
            ItemSpendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoder(binding)
    }

    override fun onBindViewHolder(holder: ChartAdapter.ViewHoder, position: Int) {
        holder.binding.imvAvtSpending.setImageBitmap(decodeBase64ToBitmap(listAdapter.get(position).Icon!!))
        holder.binding.tvNameDiretory.text = listAdapter[position].TenDanhMuc
        holder.binding.tvSpendingMoney.text = listAdapter[position].Tien.toString()
    }

    override fun getItemCount(): Int = listAdapter.size
    fun setAdapter(list: List<SpendingInChart>) {
        listAdapter.clear()
        listAdapter.addAll(list)
        notifyDataSetChanged()
    }
}