package com.rosorio.mindicador.datasources

import android.app.Application
import android.content.SharedPreferences
import com.rosorio.mindicador.security.SecurityManager
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    application = Application::class
)
class PreferencesLoginDataSourcesTest{

    private val secretKey = "/C1s/Pf0PVhe8pq1etlE++TdYOqYatxrFDzkWc8GQtQ="
    private val secretIV = "ww=="
    private val password = "mySecretText"
    private val encodedPassword = "YguOeII8XVSTdr8XS2milQ=="

    @Mock
    lateinit var preferences: SharedPreferences

    @InjectMocks
    lateinit var dataSources: PreferencesLoginDataSources

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        SecurityManager.init(secretKey, secretIV)
    }

    @Test
    fun `should login successful`() {
        val mockEditor = mock(SharedPreferences.Editor::class.java)
        `when`(preferences.edit()).thenReturn(mockEditor)
        `when`(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor)
        `when`(preferences.getString(anyString(), anyString())).thenReturn(encodedPassword)
        val login = dataSources.login("username", password)
        assertTrue(login!!)
        verify(mockEditor).putString("ACTIVE_SESSION", "username")
    }

    @Test
    fun `should login failure`() {
        `when`(preferences.getString(anyString(), anyString())).thenReturn("encodedPassword")
        val login = dataSources.login("username", password)
        assertFalse(login!!)
    }

    @Test
    fun `should return null when user doesn't exists`() {
        `when`(preferences.getString(anyString(), anyString())).thenReturn(null)
        val login = dataSources.login("username", password)
        assertNull(login)
    }

    @Test
    fun `should sign up successfully`() {
        val mockEditor = mock(SharedPreferences.Editor::class.java)
        `when`(preferences.edit()).thenReturn(mockEditor)
        dataSources.signUp("username", password)
        verify(mockEditor).putString("username", encodedPassword)

    }

    @Test
    fun `should logout successfully`() {
        val mockEditor = mock(SharedPreferences.Editor::class.java)
        `when`(preferences.edit()).thenReturn(mockEditor)
        dataSources.logout()
        verify(mockEditor).remove("ACTIVE_SESSION")

    }
}