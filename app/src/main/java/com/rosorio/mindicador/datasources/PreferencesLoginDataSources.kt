package com.rosorio.mindicador.datasources

import android.content.SharedPreferences
import com.rosorio.mindicador.security.SecurityManager

private const val ACTIVE_SESSION = "ACTIVE_SESSION"
class PreferencesLoginDataSources(
    private val preferences: SharedPreferences
): LoginDataSource {
    override fun login(username: String, password: String): Boolean? =
        preferences.getString(username, "")?.let {
            return@let when {
                it.isEmpty() -> null
                else -> {
                    val decryptPassword = SecurityManager.decrypt(it)
                    return if(password == decryptPassword) {
                        preferences.edit().putString(ACTIVE_SESSION, username).apply()
                        true
                    } else false
                }
            }
        }

    override fun signUp(username: String, password: String) = preferences.edit().run {
        val encodedPassword = SecurityManager.encrypt(password)
        putString(username, encodedPassword)
        apply()
    }

    override fun logout() = preferences.edit().run {
        remove(ACTIVE_SESSION)
        apply()
    }

    override fun activeSession(): String? = preferences.getString(ACTIVE_SESSION, "")
}