package com.example.coffeedairy

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore

class MyPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_mypage, container, false)

        // BadgeLayout 클릭 시 팝업창 나타나도록 설정
        val badgeLayout = view.findViewById<View>(R.id.BadgeLayout)
        badgeLayout.setOnClickListener {
            showBadgePopup()
        }

        // CoffeeTreeLayout 클릭 시
        val coffeeTreeLayout = view.findViewById<View>(R.id.CoffeeTreeLayout)
        coffeeTreeLayout.setOnClickListener {
            Toast.makeText(context, "다음 버전에서 만나요!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun showBadgePopup() {
        // 팝업창 나타나도록 설정
        val badgePopup = BadgePopupFragment()
        badgePopup.show(parentFragmentManager, badgePopup.tag)
    }
}

class BadgePopupFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_mypage_badge, container, false)

        var db = FirebaseFirestore.getInstance()
        var dataCountQuery = db.collection("log").count()
        var dataCount = 0
        dataCountQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Count fetched successfully
                val snapshot = task.result
                dataCount = snapshot.count.toInt()
            }
        }

            val badge5days = view.findViewById<View>(R.id.badgeImage5days)
            val badge10days = view.findViewById<View>(R.id.badgeImage10days)

            if (dataCount < 5) {
                badge5days.setBackgroundColor(Color.parseColor("#808080"))
            }
            if (dataCount < 10) {
                badge10days.setBackgroundColor(Color.parseColor("#808080"))
            }

            // Button 클릭 시 팝업창 닫기
            val closeButton = view.findViewById<Button>(R.id.Button)
            closeButton.setOnClickListener {
                dismiss()
            }

        return view
    }
}
