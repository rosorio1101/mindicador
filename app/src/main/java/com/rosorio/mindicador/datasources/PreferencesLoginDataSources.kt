package com.rosorio.mindicador.datasources

import android.content.SharedPreferences
import com.rosorio.mindicador.security.SecurityManager

class PreferencesLoginDataSources(
    private val preferences: SharedPreferences
): LoginDataSource {
    override fun login(username: String, password: String): Boolean? =
        preferences.getString(username, "")?.let {
            return@let when {
                it.isEmpty() -> null
                else -> {
                    val decryptPassword = SecurityManager.decrypt(it)
                    return password == decryptPassword
                }
            }
        }

    override fun signUp(username: String, password: String) = preferences.edit().run {
        val encodedPassword = SecurityManager.encrypt(password)
        putString(username, encodedPassword)
        apply()
    }

    override fun logout(username: String) = preferences.edit().run {
        remove(username)
        apply()
    }
}