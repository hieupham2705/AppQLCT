package com.example.appqlct.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appqlct.R
import com.example.appqlct.actitities.ShareMoneyActivity
import com.example.appqlct.base.DataBaseManager
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
            binding.lnDelete.setOnClickListener {
                showYesNoDialog()
            }
        }
    }
    private fun showYesNoDialog() {
        val builder = AlertDialog.Builder(context)

        // Thiết lập tiêu đề và nội dung của dialog
        builder.setTitle("Câu hỏi")
            .setMessage("Bạn có chắc chắn muốn xóa toàn bộ các\n" +
                    "dữ liệu không? Thao tác này không thể\n" +
                    "hoàn tác lại.")

        // Thiết lập nút Yes
        builder.setPositiveButton("Yes") { dialog, which ->
            DataBaseManager.deleteAllTable()
            dialog.dismiss()
        }

        // Thiết lập nút No
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        // Hiển thị dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}