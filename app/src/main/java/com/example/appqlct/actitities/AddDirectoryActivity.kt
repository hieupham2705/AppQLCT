package com.example.appqlct.actitities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appqlct.R
import com.example.appqlct.adapter.DirectoryAdapter
import com.example.appqlct.base.DataBaseManager
import com.example.appqlct.databinding.ActivityAddDirectoryBinding
import com.example.appqlct.model.DanhMuc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDirectoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddDirectoryBinding.inflate(layoutInflater) }
    private val adapter by lazy {
        DirectoryAdapter(onCickEditDirectory = {

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            recyclerview.adapter = adapter
            imbtnBack.setOnClickListener {
                finish()
            }
            btnSave.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    DataBaseManager.getInstance(applicationContext).getItemDAO()
                        .themDanhMuc(
                            DanhMuc(
                                tenDanhMuc = edtName.editText?.text.toString(),
//                                icon = ,
//                                thuChi =
                            )
                        )
                }
            }
        }
    }
}