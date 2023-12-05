package com.example.appqlct.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.ItemDirectoryBinding
import com.example.appqlct.databinding.ItemNothingBinding
import com.example.appqlct.databinding.ItemSpendingBinding
import com.example.appqlct.databinding.ItemTimeBinding
import com.example.appqlct.extension.decodeBase64ToBitmap
import com.example.appqlct.model.SpendingInCalendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "CalendarAdapter"

class CalendarAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val typeTime = 0;
    private val typeSpending = 1;
    private var time = ""
    private val listSpending = mutableListOf<SpendingInCalendar>()

    class TimeViewHolder(val binding: ItemTimeBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class SpendingViewHolder(val binding: ItemSpendingBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun getItemViewType(position: Int): Int {
        val positionTime = 0;
        return when (position) {
            positionTime -> typeTime
            else -> typeSpending
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val bindingTime = ItemTimeBinding.inflate(layoutInflater, parent, false)
        val bindingSpending = ItemSpendingBinding.inflate(layoutInflater, parent, false)
        return when (viewType) {
            typeTime -> TimeViewHolder(bindingTime)
            else -> SpendingViewHolder(bindingSpending)
        }
    }

    override fun getItemCount(): Int = listSpending.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TimeViewHolder) {
            holder.binding.root.text = time
        }
        if (holder is SpendingViewHolder) {
            holder.binding.apply {
                listSpending[position - 1].avtSpending?.let { imvAvtSpending.setImageResource(it) }
                root.setOnLongClickListener {
                    showPopupMenu(holder.itemView)
                    true
                }
                CoroutineScope(Dispatchers.Main).launch {
                    val danhMuc =
                        DataBaseManager.getInstance(context).getItemDAO()
                            .timKiemDanhMuc(listSpending.get(position - 1).idDirectory!!)
                    tvNameDiretory.text = danhMuc.tenDanhMuc
                    imvAvtSpending.setImageBitmap(decodeBase64ToBitmap(danhMuc.icon!!))
                }
                if (listSpending[position - 1].check == true)
                    tvSpendingMoney.text = "-" + listSpending[position - 1].money
                else
                    tvSpendingMoney.text = "+" + listSpending[position - 1].money
            }
        }

    }

    fun setAdapter(Time: String, list: List<SpendingInCalendar>) {
        time = Time
        listSpending.clear()
        listSpending.addAll(list)
        Log.e(TAG, "setAdapter: ${listSpending.size}")
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