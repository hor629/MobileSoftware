package com.example.coffeedairy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeedairy.databinding.ActivityDateLogBinding

// 특정 일자의 기록 열람을 위한 액티비티
class DateLogActivity: AppCompatActivity() {
    lateinit var binding: ActivityDateLogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDateLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}