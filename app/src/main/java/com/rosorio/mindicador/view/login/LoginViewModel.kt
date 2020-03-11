package com.rosorio.mindicador.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rosorio.mindicador.view.commons.ScreenState
import com.rosorio.mindicador.datasources.LoginDataSource

class LoginViewModel(val loginInteractor: LoginInteractor): ViewModel(),
    LoginInteractor.OnLoginFinishListener {
    private val _state = MutableLiveData<ScreenState<LoginState>>()

    val state: LiveData<ScreenState<LoginState>>
        get() = _state

    fun verifyActiveSession() {
        val activeUser = loginInteractor.hasActiveSession()
        if(!activeUser.isNullOrEmpty()) {
            _state.value = ScreenState.Render(LoginState.Success(activeUser))
        }
    }

    fun performLogin(username: String, password: String){
        _state.value = ScreenState.Loading
        loginInteractor.login(username, password, this)
    }

    override fun onSuccess(username: String?) {
        _state.value = ScreenState.Render(LoginState.Success(username!!))
    }

    override fun onCredentialsError() {
        _state.value = ScreenState.Render(LoginState.WrongCredentials)
    }
}