package com.example.android.recklinetest.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.recklinetest.R
import com.example.android.recklinetest.util.SharedPreferencesManager

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        SharedPreferencesManager.initialize(this)
    }
}