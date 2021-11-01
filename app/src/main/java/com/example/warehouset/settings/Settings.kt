package com.example.warehouset.settings

import android.content.Context
import android.content.SharedPreferences

class Settings(context: Context) {

    companion object {
        const val IS_APP_FIRST_LAUNCHED = "isAppFirstLaunched"
        const val BOOLEAN = "accessToken"
        const val USERNAME = "infoAddress"
        const val USER_ROLE = "role"
        const val UUID = "uuidEmployee"
        const val URL: String = "http://back-end.stroyshop.uz/"
        const val HEADERACCEPT: String = "application/json"
        const val HEADERXRW: String = "XMLHttpRequest"
    }



    private val preferences: SharedPreferences =
        context.getSharedPreferences("WarehousePreferences", Context.MODE_PRIVATE)

    var isFirstLaunched: Boolean
        set(value) {
            preferences.edit().putBoolean(IS_APP_FIRST_LAUNCHED, value).apply()
        }
        get() = preferences.getBoolean(IS_APP_FIRST_LAUNCHED, true)

    var userId: Int
        set(value) = preferences.edit().putInt(USERNAME, value).apply()
        get() = preferences.getInt(USERNAME, 0)
    var status: Boolean
        set(value) = preferences.edit().putBoolean(BOOLEAN, value).apply()
        get() = preferences.getBoolean(BOOLEAN, false)

    var token: String
        set(value) = preferences.edit().putString(UUID, value).apply()
        get() = preferences.getString(UUID, "") ?: ""

    var role: String
        set(value) = preferences.edit().putString(USER_ROLE, value).apply()
        get() = preferences.getString(USER_ROLE, "") ?: ""
}