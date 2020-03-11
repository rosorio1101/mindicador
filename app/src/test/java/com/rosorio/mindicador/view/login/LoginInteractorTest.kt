package com.rosorio.mindicador.view.login

import com.rosorio.mindicador.datasources.LoginDataSource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class LoginInteractorTest {

    @Mock
    lateinit var loginDataSource: LoginDataSource

    @InjectMocks
    lateinit var loginInteractor: LoginInteractor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should login success`() {
        val username = "username"
        val password = "password"
        val mockListener = mock(LoginInteractor.OnLoginFinishListener::class.java)

        `when`(loginDataSource.login(anyString(), anyString())).thenReturn(true)

        loginInteractor.login(username, password, mockListener)

        verify(mockListener).onSuccess("username")
    }

    @Test
    fun `should login failure`() {
        val username = "username"
        val password = "password"
        val mockListener = mock(LoginInteractor.OnLoginFinishListener::class.java)

        `when`(loginDataSource.login(anyString(), anyString())).thenReturn(false)

        loginInteractor.login(username, password, mockListener)

        verify(mockListener).onCredentialsError()
    }

    @Test
    fun `should login failure when username empty`() {
        val username = ""
        val password = "password"
        val mockListener = mock(LoginInteractor.OnLoginFinishListener::class.java)
        loginInteractor.login(username, password, mockListener)
        verify(mockListener).onCredentialsError()
    }

    @Test
    fun `should login failure when username null`() {
        val username = null
        val password = "password"
        val mockListener = mock(LoginInteractor.OnLoginFinishListener::class.java)
        loginInteractor.login(username, password, mockListener)
        verify(mockListener).onCredentialsError()
    }

    @Test
    fun `should login failure when password empty`() {
        val username = "username"
        val password = ""
        val mockListener = mock(LoginInteractor.OnLoginFinishListener::class.java)
        loginInteractor.login(username, password, mockListener)
        verify(mockListener).onCredentialsError()
    }


    @Test
    fun `should login failure when password null`() {
        val username = "username"
        val password = null
        val mockListener = mock(LoginInteractor.OnLoginFinishListener::class.java)
        loginInteractor.login(username, password, mockListener)
        verify(mockListener).onCredentialsError()
    }


    @Test
    fun `should do nothing when listener is null`() {
        val username = "username"
        val password = "password"
        val mockListener = mock(LoginInteractor.OnLoginFinishListener::class.java)
        loginInteractor.login(username, password,null)
        verify(mockListener, never()).onCredentialsError()
    }
    @Test
    fun `should create user and login success`() {
        val username = "username"
        val password = "password"
        val mockListener = mock(LoginInteractor.OnLoginFinishListener::class.java)

        `when`(loginDataSource.login(anyString(), anyString()))
            .thenReturn(null)
            .thenReturn(true)

        loginInteractor.login(username, password, mockListener)

        verify(loginDataSource).signUp(username, password)
        verify(mockListener).onSuccess("username")
    }

    @Test
    fun `should active session`() {
        `when`(loginDataSource.activeSession()).thenReturn("username")
        val username = loginInteractor.activeSession()
        assertEquals("username", username)
        verify(loginDataSource).activeSession()
    }

}