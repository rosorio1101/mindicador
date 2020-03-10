package com.rosorio.mindicador.datasources

import android.content.SharedPreferences

class PreferencesLoginDataSources(
    private val preferences: SharedPreferences
): LoginDataSource {
    override fun login(username: String, password: String): Boolean = preferences.getString(username, "").let { !it.isNullOrEmpty() }

    override fun logout(username: String) = preferences.edit().run {
        remove(username)
        apply()
    }
}