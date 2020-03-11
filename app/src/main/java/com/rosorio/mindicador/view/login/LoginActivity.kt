package com.rosorio.mindicador.view.login

import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.rosorio.mindicador.R
import com.rosorio.mindicador.view.commons.ScreenState
import com.rosorio.mindicador.view.commons.viewmodels.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<LoginViewModel>

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        viewModel.state.observe(::getLifecycle,::updateUI)
        btnLogin.setOnClickListener(::onLoginClicked)
    }

    private fun updateUI(screenState: ScreenState<LoginState>?) {
        when (screenState) {
            ScreenState.Loading -> showLoading()
            is ScreenState.Render -> updateRenderState(screenState.renderState)
        }
    }

    private fun updateRenderState(render: LoginState) {
        hideLoading()
        when(render) {
            LoginState.Success -> onSuccess()
            LoginState.WrongCredentials -> onError()
        }
    }

    private fun onSuccess() {
        errorMessage.text = "Inicio de sesión correcto"
        errorMessage.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
    }

    private fun onError() {
        errorMessage.text = "Credenciales Invalidas"
        errorMessage.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
    }

    private fun showLoading(){
        window.setFlags(FLAG_NOT_TOUCHABLE, FLAG_NOT_TOUCHABLE)
        loadingView.showLoading()
    }

    private fun hideLoading() {
        window.clearFlags(FLAG_NOT_TOUCHABLE)
        loadingView.hideLoading()
    }

    private fun onLoginClicked(view: View) {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        viewModel.performLogin(username, password)
    }
}
