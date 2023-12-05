package com.example.appqlct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlct.databinding.ItemEditDirectoryBinding
import com.example.appqlct.extension.decodeBase64ToBitmap
import com.example.appqlct.model.DanhMuc

class EditDirectoryAdapter : RecyclerView.Adapter<EditDirectoryAdapter.ViewHolder>() {
    private val listAdapter = mutableListOf<DanhMuc>()
    class ViewHolder(val binding: ItemEditDirectoryBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditDirectoryAdapter.ViewHolder {
        return ViewHolder(ItemEditDirectoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = listAdapter.size
    override fun onBindViewHolder(holder: EditDirectoryAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            tvNameDiretory.text = listAdapter[position].tenDanhMuc
            imvIcon.setImageBitmap(decodeBase64ToBitmap(listAdapter[position].icon!!))
        }
    }
    fun setAdapter(list: List<DanhMuc>){
        listAdapter.clear()
        listAdapter.addAll(list)
        notifyDataSetChanged()
    }
}