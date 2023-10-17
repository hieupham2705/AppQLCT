package com.example.appqlct.adapter

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlct.R
import com.example.appqlct.databinding.FragmentEditBinding
import com.example.appqlct.databinding.ItemDirectoryBinding
import com.example.appqlct.model.Directory

private const val TAG = "DirectoryAdapter"

class DirectoryAdapter(
    private val onCickEditDirectory: () -> Unit
) : RecyclerView.Adapter<DirectoryAdapter.ViewHolder>() {
    private val listDirectory = mutableListOf<Directory>()
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
            listDirectory[position].image?.let { imvDirectory.setImageResource(it) }
            tvNameDiretory.text = listDirectory[position].text
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
            }else
                root.setBackgroundResource(R.drawable.border_directory)

        }
    }


    override fun getItemCount(): Int = listDirectory.size
    fun setAdapter(list: List<Directory>) {
        listDirectory.clear()
        listDirectory.addAll(list)
        notifyDataSetChanged()
    }
    fun getDirectory():String{return directory}
}

