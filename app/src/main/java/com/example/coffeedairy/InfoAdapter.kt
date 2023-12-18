package com.example.coffeedairy

import Info
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class InfoAdapter(private var infoList: List<Info>, private val context: Context) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    private var alertDialog: AlertDialog? = null  // AlertDialog를 클래스 레벨에서 선언

    private var filteredInfoList: List<Info> = infoList  // 검색 결과를 담을 리스트 추가

    fun updateData(newInfoList: List<Info>) {
        infoList = newInfoList
        filterResults("")  // 초기에는 전체 목록을 보여줌
        notifyDataSetChanged()
    }

    //검색 기능 추가
    fun filterResults(query: String) {
        filteredInfoList = if (query.isBlank()) {
            infoList
        } else {
            val regex = Regex(".*${query.trim()}.*", RegexOption.IGNORE_CASE)
            infoList.filter { regex.containsMatchIn(it.title) }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_info_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = filteredInfoList[position]

        Glide.with(holder.itemView.context)
            .load(info.imagePath)
            .into(holder.infoImage)
        holder.infoTitle.text = info.title
        holder.infoOverView.text = info.overview

        holder.viewInfoButton.setOnClickListener {
            // 클릭된 아이템에 해당하는 정보를 팝업창으로 표시
            showInfoPopup(info)
        }
    }

    private fun showInfoPopup(info: Info) {
        // AlertDialog 빌더 생성
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.activity_info_popup, null)

        // 팝업창 내부의 뷰들을 초기화
        val cafeInfoImage = view.findViewById<ImageView>(R.id.cafeInfoImage)
        val cafeInfoTitle = view.findViewById<TextView>(R.id.cafeInfoTitle)
        val cafeInfoContent = view.findViewById<TextView>(R.id.cafeInfoContent)
        val closeButton = view.findViewById<Button>(R.id.viewInfoButton)

        // 정보 채우기
        Glide.with(context)
            .load(info.imagePath)
            .into(cafeInfoImage)
        cafeInfoTitle.text = info.title
        cafeInfoContent.text = info.content

        // 닫기 버튼 클릭 이벤트 처리
        closeButton.setOnClickListener {
            alertDialog?.dismiss()
        }

        // AlertDialog 생성 및 표시
        alertDialog = builder.setView(view).create()
        alertDialog?.show()
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val infoImage: ImageView = itemView.findViewById(R.id.cafeInfoImage)
        val infoTitle: TextView = itemView.findViewById(R.id.cafeInfoTitle)
        val infoOverView: TextView = itemView.findViewById(R.id.cafeInfoOverview)
        val viewInfoButton: Button = itemView.findViewById(R.id.viewInfoButton)
    }
}

