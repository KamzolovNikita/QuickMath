package com.example.android.recklinetest.util

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties

object SharedPreferencesManager {
    private lateinit var prefs: SharedPreferences

    private const val FILE_NAME  = "user_info"

    private const val USER_RECORD = "user_record"
    private const val USER_OPENED_NOTIFICATION = "user_opened_notification"

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    fun saveNewRecord(record: Int) {
        prefs.edit()
            .putInt(USER_RECORD, record)
            .apply()
    }

    fun getRecord(): Int {
        return prefs.getInt(USER_RECORD, 0)
    }

    fun saveNotificationOpened() {
        prefs.edit()
            .putBoolean(USER_OPENED_NOTIFICATION, true)
            .apply()
    }

    fun getNotificationOpened(): Boolean {
        return prefs.getBoolean(USER_OPENED_NOTIFICATION, false)
    }

}