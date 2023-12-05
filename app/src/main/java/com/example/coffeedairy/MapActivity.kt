package com.example.coffeedairy

import MapFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment

    }
}
