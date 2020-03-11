package com.rosorio.mindicador.datasources

import android.app.Application
import android.content.SharedPreferences
import com.rosorio.mindicador.security.SecurityManager
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
        `when`(preferences.getString(anyString(), anyString())).thenReturn(encodedPassword)
        val login = dataSources.login("username", password)
        assertTrue(login!!)
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
}