package com.rosorio.mindicador.view.login

import com.rosorio.mindicador.datasources.LoginDataSource

class LoginInteractor(
    private val loginDataSource: LoginDataSource
) {
    interface OnLoginFinishListener {
        fun onSuccess(username: String?)
        fun onCredentialsError()
    }

    fun login(username: String?, password: String?, listener: OnLoginFinishListener?) {
        when {
            username.isNullOrEmpty() -> listener?.onCredentialsError()
            password.isNullOrEmpty() -> listener?.onCredentialsError()
            else -> when(loginDataSource.login(username, password)) {
                true -> listener?.onSuccess(username)
                false -> {
                    listener?.onCredentialsError()
                }
                null -> {
                    loginDataSource.signUp(username, password)
                    login(username, password, listener)
                }
            }
        }
    }

    fun hasActiveSession(): String? = loginDataSource.activeSession()
}