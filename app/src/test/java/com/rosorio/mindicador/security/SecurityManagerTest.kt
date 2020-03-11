package com.rosorio.mindicador.security

import android.app.Application
import org.junit.After
import org.junit.Assert.assertEquals

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.RuntimeException


@RunWith(RobolectricTestRunner::class)
@Config(
    application = Application::class
)
class SecurityManagerTest {

    private val secretKey = "/C1s/Pf0PVhe8pq1etlE++TdYOqYatxrFDzkWc8GQtQ="
    private val secretIV = "ww=="
    private val textToEncode = "mySecretText"
    private val textToDecode = "YguOeII8XVSTdr8XS2milQ=="

    @Test
    fun `should init successfully`() {
        SecurityManager.init(secretKey, secretIV)
    }

    @Test(expected = RuntimeException::class)
    fun `should throws exception when secret key is empty`() {
        SecurityManager.init("",secretIV)
    }

    @Test(expected = RuntimeException::class)
    fun `should throws exception when secret IV is empty`() {
        SecurityManager.init(secretKey,"")
    }

    @Test
    fun `should encrypt successfully`() {
        SecurityManager.init(secretKey, secretIV)
        val encryptValue = SecurityManager.encrypt(textToEncode)
        assertEquals("encrypt successful",textToDecode, encryptValue)
    }

    @Test
    fun `should decrypt successfully`() {
        SecurityManager.init(secretKey, secretIV)
        val decryptValue = SecurityManager.decrypt(textToDecode)
        assertEquals("encrypt successful",textToEncode, decryptValue)
    }

    @After
    fun tearDown() {
    }
}