package com.example.coffeedairy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

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

        // CoffeeTreeLayout 클릭 시 팝업창 나타나도록 설정
        val coffeeTreeLayout = view.findViewById<View>(R.id.CoffeeTreeLayout)
        coffeeTreeLayout.setOnClickListener {
            showCoffeeTreePopup()
        }

        return view
    }

    private fun showBadgePopup() {
        // 팝업창 나타나도록 설정
        val badgePopup = BadgePopupFragment()
        badgePopup.show(parentFragmentManager, badgePopup.tag)
    }

    private fun showCoffeeTreePopup() {
        // 팝업창 나타나도록 설정
        val coffeeTreePopup = CoffeeTreePopupFragment()
        coffeeTreePopup.show(parentFragmentManager, coffeeTreePopup.tag)
    }
}

class BadgePopupFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_mypage_badge, container, false)

        // Button 클릭 시 팝업창 닫기
        val closeButton = view.findViewById<Button>(R.id.Button)
        closeButton.setOnClickListener {
            dismiss()
        }

        return view
    }
}

class CoffeeTreePopupFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_mypage_coffeetree, container, false)

        // Button 클릭 시 팝업창 닫기
        val closeButton = view.findViewById<Button>(R.id.Button)
        closeButton.setOnClickListener {
            dismiss()
        }

        return view
    }
}
