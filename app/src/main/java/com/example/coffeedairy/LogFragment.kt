package com.example.coffeedairy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeedairy.databinding.ActivityLogBinding
import com.example.coffeedairy.databinding.ActivityMainBinding
import com.example.coffeedairy.LogData

class LogFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
        : View?{
        //activity?.supportActionBar?.title = "기록"

        val binding = ActivityLogBinding.inflate(layoutInflater, container, false)

        val layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = GridAdapter()


        return binding.root
    }
}