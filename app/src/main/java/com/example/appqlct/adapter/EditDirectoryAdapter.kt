package com.example.appqlct.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlct.databinding.ItemEditDirectoryBinding
import com.example.appqlct.extension.decodeBase64ToBitmap
import com.example.appqlct.model.DanhMuc

class EditDirectoryAdapter(private val context: Context) : RecyclerView.Adapter<EditDirectoryAdapter.ViewHolder>() {
    private val listAdapter = mutableListOf<DanhMuc>()
    class ViewHolder(val binding: ItemEditDirectoryBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditDirectoryAdapter.ViewHolder {
        return ViewHolder(ItemEditDirectoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = listAdapter.size
    override fun onBindViewHolder(holder: EditDirectoryAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            root.setOnLongClickListener {
                showPopupMenu(holder.itemView)
                true
            }
            tvNameDiretory.text = listAdapter[position].tenDanhMuc
            imvIcon.setImageBitmap(decodeBase64ToBitmap(listAdapter[position].icon!!))
        }
    }
    fun setAdapter(list: List<DanhMuc>){
        listAdapter.clear()
        listAdapter.addAll(list)
        notifyDataSetChanged()
    }
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(context, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(com.example.appqlct.R.menu.menu_directory, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                com.example.appqlct.R.id.edit -> {

                    true
                }

                com.example.appqlct.R.id.delete -> {

                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }
}