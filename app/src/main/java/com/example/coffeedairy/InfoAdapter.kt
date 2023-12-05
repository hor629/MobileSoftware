package com.example.coffeedairy

import Info
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InfoAdapter(private val infoList: List<Info>) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_info_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = infoList[position]

        //holder.infoImage.setImageResource(info.imagePath)
        holder.infoTitle.text = info.title
        holder.infoContent.text = info.content
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val infoImage: ImageView = itemView.findViewById(R.id.cafeInfoImage)
        val infoTitle: TextView = itemView.findViewById(R.id.cafeInfoTitle)
        val infoContent: TextView = itemView.findViewById(R.id.cafeInfoContent)
        val viewInfoButton: Button = itemView.findViewById(R.id.viewInfoButton)
    }
}

