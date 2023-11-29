package com.example.appqlct.actitities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appqlct.R
import com.example.appqlct.databinding.ActivityShareMoneyBinding

class ShareMoneyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityShareMoneyBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}