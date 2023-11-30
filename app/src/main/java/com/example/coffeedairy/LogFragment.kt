package com.example.coffeedairy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeedairy.databinding.ActivityLogBinding
import com.example.coffeedairy.databinding.ActivityMainBinding

class LogFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
        : View?{
        //activity?.supportActionBar?.title = "기록"

        val binding = ActivityLogBinding.inflate(layoutInflater, container, false)
        val datas = mutableListOf<String>("키위", "포도", "딸기",
            "망고", "바나나", "블루베리", "사과")
        val images = mutableListOf<Int>()
        val prices = mutableListOf<String>("1000원", "2000원", "3000원",
            "4000원", "5000원", "6000원", "7000원")

        val layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = GridAdapter(datas)

        return binding.root
    }
}