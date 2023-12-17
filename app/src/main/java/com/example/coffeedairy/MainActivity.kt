package com.example.coffeedairy

import InfoFragment
import MapFragment
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coffeedairy.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
// 커밋용 수정사항
class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    val db = Firebase.firestore

    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments: List<Fragment>

        init{
            fragments = listOf(CalendarFragment(),
                LogFragment(), MapFragment(), InfoFragment(), MyPageFragment())
        }

        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewpager.adapter = MyFragmentPagerAdapter(this)

        val tabTitles = listOf("캘린더", "기록", "지도", "정보", "마이페이지")
        val tabIcon = listOf(R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background)
        TabLayoutMediator(binding.tabs, binding.viewpager){
                tab, position -> tab.text = tabTitles[position]
            tab.setIcon(tabIcon[position])
            supportActionBar?.setTitle(tabTitles[position])
        }.attach()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var pressedTime : Long = 0
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로 버튼 이벤트 처리
                if (System.currentTimeMillis() > pressedTime + 2000) {
                    pressedTime = System.currentTimeMillis()
                    Toast.makeText(this@MainActivity, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    finish()
                }
            }
        }

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> return true
        }
        return super.onOptionsItemSelected(item)
    }
}