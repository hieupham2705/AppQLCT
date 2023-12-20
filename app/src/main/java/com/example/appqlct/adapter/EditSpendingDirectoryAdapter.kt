package com.example.appqlct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlct.R
import com.example.appqlct.databinding.ItemDirectoryBinding
import com.example.appqlct.extension.decodeBase64ToBitmap
import com.example.appqlct.model.DanhMuc
import com.example.appqlct.model.Directory

private const val TAG = "EditSpendingDirectoryAdapter"

class EditSpendingDirectoryAdapter(
) : RecyclerView.Adapter<EditSpendingDirectoryAdapter.ViewHolder>() {
    private val listDirectory = mutableListOf<DanhMuc>()
    private var index = 0
    class ViewHolder(val binding: ItemDirectoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditSpendingDirectoryAdapter.ViewHolder {
        val binding =
            ItemDirectoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EditSpendingDirectoryAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            imvDirectory.setImageBitmap(decodeBase64ToBitmap(listDirectory[position].icon!!))
            tvNameDiretory.text = listDirectory[position].tenDanhMuc
            root.setOnClickListener {
                val i = position
                index = i
                notifyDataSetChanged()
            }
            if (position == index) {
                root.setBackgroundResource(R.drawable.border_btn_tienthu)
            } else
                root.setBackgroundResource(R.drawable.border_directory)

        }
    }


    override fun getItemCount(): Int = listDirectory.size
    fun setAdapter(list: List<DanhMuc>) {
        listDirectory.clear()
        listDirectory.addAll(list)
        notifyDataSetChanged()
    }

    fun getIndex(): Int {
        return index
    }
}

