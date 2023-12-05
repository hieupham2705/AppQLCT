package com.example.appqlct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlct.R
import com.example.appqlct.databinding.ItemDirectoryBinding
import com.example.appqlct.extension.decodeBase64ToBitmap
import com.example.appqlct.model.DanhMuc
import com.example.appqlct.model.Directory

private const val TAG = "DirectoryAdapter"

class DirectoryAdapter(
    private val onCickEditDirectory: () -> Unit
) : RecyclerView.Adapter<DirectoryAdapter.ViewHolder>() {
    private val listDirectory = mutableListOf<DanhMuc>()
    private var item = 0
    private var directory = "Ăn uống"

    class ViewHolder(val binding: ItemDirectoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryAdapter.ViewHolder {
        val binding =
            ItemDirectoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DirectoryAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            imvDirectory.setImageBitmap(decodeBase64ToBitmap(listDirectory[position].icon!!))
            tvNameDiretory.text = listDirectory[position].tenDanhMuc
            root.setOnClickListener {
                val i = position
                item = i
                directory = tvNameDiretory.text.toString()
                if (position != listDirectory.size - 1) {
                    notifyDataSetChanged()
                } else
                    onCickEditDirectory.invoke()
            }
            if (position == item) {
                root.setBackgroundResource(R.drawable.border_btn_tienthu)
            } else
                root.setBackgroundResource(R.drawable.border_directory)

        }
    }


    override fun getItemCount(): Int = listDirectory.size
    fun setAdapter(list: List<DanhMuc>) {
        listDirectory.clear()
        listDirectory.addAll(list)
        listDirectory.add(DanhMuc(tenDanhMuc = "Chỉnh sửa"))
        notifyDataSetChanged()
    }

    fun getDirectory(): String {
        return directory
    }

    fun getImage(): String {
        return listDirectory.get(item).icon!!
    }
}

