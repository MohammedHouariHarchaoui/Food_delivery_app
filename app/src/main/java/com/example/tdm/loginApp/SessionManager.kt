package com.example.tdm.loginApp

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "LoginSession",
        Context.MODE_PRIVATE
    )

    companion object {
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        const val KEY_USER_ID = "userId" // Add a constant for the user ID key
    }

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var userId: Int
        get() = sharedPreferences.getInt(KEY_USER_ID, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_USER_ID, value).apply()
}
