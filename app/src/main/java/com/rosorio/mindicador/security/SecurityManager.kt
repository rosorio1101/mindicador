package com.rosorio.mindicador.security

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object SecurityManager {
    const val AES = "AES"
    private var secretKey: SecretKey? = null
    private var IV: ByteArray ? = null

    fun encrypt(text: String): String? {
        val cipher: Cipher = Cipher.getInstance(AES)
        val ivSpec = IvParameterSpec(IV)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
        return encode(cipher.doFinal(text.toByteArray()))
    }

    fun decrypt(text: String): String? {
        try {
            val cipher = Cipher.getInstance(AES)
            val ivSpec = IvParameterSpec(IV)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
            return decode(text)?.let {
                String(cipher.doFinal(it))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun init(secretKey: String, IV: String) {
        if (secretKey.isEmpty() || IV.isEmpty()) {
            val generatedSecretKey = generateSecretKey()
            val generatedIV = generateIV()
            throw RuntimeException("""

################################################################################################################

                Perdón pero por razones de seguridad no podra ingresar a la aplicación
                hasta que reemplaze los siguientes valores en el archivo app/build.gradle
                
                buildConfigField "String", "SECRET_KEY", "\"${generatedSecretKey?.trim()}\""
                buildConfigField "String", "SECRET_IV", "\"${generatedIV?.trim()}\""

################################################################################################################

            """.trimIndent())
        }

        this.secretKey = decode(secretKey)?.let {
            SecretKeySpec(it, 0, it.size, AES)
        }
        this.IV = decode(IV)
    }

    private fun generateSecretKey(): String? = KeyGenerator.getInstance(AES).apply {
            init(256)
        }.let {
        val secretKey = it.generateKey()
        encode(secretKey.encoded)
    }

    private fun generateIV(): String?  = byteArrayOf(16).let {
        val secureRandom = SecureRandom()
        secureRandom.nextBytes(it)
        encode(it)
    }

    private fun encode(bytes: ByteArray) : String? = Base64.encodeToString(bytes,Base64.NO_WRAP)
    private fun decode(text: String): ByteArray? = Base64.decode(text, Base64.NO_WRAP)
}