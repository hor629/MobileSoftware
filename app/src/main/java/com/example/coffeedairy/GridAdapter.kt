package com.example.coffeedairy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeedairy.databinding.LogItemgridRecyclerviewBinding

class GridHolder (val binding:LogItemgridRecyclerviewBinding):
    RecyclerView.ViewHolder(binding.root)
class GridAdapter (val datas:MutableList<String>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun getItemCount(): Int = datas.size
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                RecyclerView.ViewHolder = GridHolder(LogItemgridRecyclerviewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ))

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as GridHolder).binding
            binding.logCoffeeName.text = datas[position]

            binding.itemGrid.setOnClickListener {
                val intent = Intent(binding.root.context, DateLogActivity::class.java)
                startActivity(binding.root.context, intent, null)
            }
        }
    }
