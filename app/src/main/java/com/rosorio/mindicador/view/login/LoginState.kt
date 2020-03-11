package com.rosorio.mindicador.view.login

sealed class LoginState {
    class Success(val username: String): LoginState()
    object WrongCredentials: LoginState()
}