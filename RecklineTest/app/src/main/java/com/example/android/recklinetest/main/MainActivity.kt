package com.example.android.recklinetest.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.recklinetest.R
import com.example.android.recklinetest.game.GameActivity
import com.example.android.recklinetest.util.SharedPreferencesManager
import com.example.android.recklinetest.web.WebActivity
import com.onesignal.OneSignal


class MainActivity : AppCompatActivity() {

    private val ONESIGNAL_APP_ID = "37a8e12e-6995-4428-820b-71b4959a6d9d"
    var opened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferencesManager.initialize(this)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        OneSignal.setNotificationOpenedHandler {
            SharedPreferencesManager.saveNotificationOpened()
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
            opened = true
        }

        if(!SharedPreferencesManager.getNotificationOpened()) {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
        else {
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }

}