package com.rosorio.mindicador.view.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rosorio.mindicador.view.commons.ScreenState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Mock
    lateinit var loginInteractor: LoginInteractor

    @InjectMocks
    lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should perform login success`(){
        val mockedObserver = createStateObserver()
        val username = "user"
        val password = "password"

        doAnswer {
            (it.arguments[2] as LoginInteractor.OnLoginFinishListener).onSuccess()
                null
            }.`when`(loginInteractor).login(any(), any(), any())

        loginViewModel.state.observeForever(mockedObserver)
        loginViewModel.performLogin(username, password)

        verify(loginInteractor, atLeastOnce()).login(eq(username), eq(password), any())

        verify(mockedObserver).onChanged(ArgumentMatchers.argThat {
            it is ScreenState.Loading
        })

        verify(mockedObserver).onChanged(ArgumentMatchers.argThat {
            it is ScreenState.Render<LoginState> && it.renderState == LoginState.Success
        })

    }

    @Test
    fun `should perform login failure`(){
        val mockedObserver = createStateObserver()
        val username = "user"
        val password = "password"

        doAnswer {
            (it.arguments[2] as LoginInteractor.OnLoginFinishListener).onCredentialsError()
            null
        }.`when`(loginInteractor).login(any(), any(), any())

        loginViewModel.state.observeForever(mockedObserver)
        loginViewModel.performLogin(username, password)

        verify(loginInteractor, atLeastOnce()).login(eq(username), eq(password), any())

        verify(mockedObserver).onChanged(ArgumentMatchers.argThat {
            it is ScreenState.Loading
        })

        verify(mockedObserver).onChanged(ArgumentMatchers.argThat {
            it is ScreenState.Render<LoginState> && it.renderState == LoginState.WrongCredentials
        })
    }

    private fun createStateObserver(): Observer<ScreenState<LoginState>> = spy(Observer {  })
}