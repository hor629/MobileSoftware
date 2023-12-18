package com.example.coffeedairy

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        binding.recyclerView.run {
            val spanCount = 2
            val space = 20 //20dp로 간격 지정
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }


        return binding.root
    }
}

class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount + 1      // 1부터 시작

        // 첫 행 제외하고 상단 여백 추가
        if (position >= spanCount) {
            outRect.top = space
        }
        outRect.bottom = space
        // 첫번째 열을 제외하고 좌측 여백 추가
        if (column != 1) {
            outRect.left = space
        }
    }
}