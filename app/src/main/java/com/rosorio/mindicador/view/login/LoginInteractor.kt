package com.rosorio.mindicador.view.login

import com.rosorio.mindicador.datasources.LoginDataSource

class LoginInteractor(
    private val loginDataSource: LoginDataSource
) {
    interface OnLoginFinishListener {
        fun onSuccess()
        fun onCredentialsError()
    }

    fun login(username: String?, password: String?, listener: OnLoginFinishListener?) {
        when {
            username.isNullOrEmpty() -> listener?.onCredentialsError()
            password.isNullOrEmpty() -> listener?.onCredentialsError()
            else -> when(loginDataSource.login(username, password)) {
                true -> listener?.onSuccess()
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
}