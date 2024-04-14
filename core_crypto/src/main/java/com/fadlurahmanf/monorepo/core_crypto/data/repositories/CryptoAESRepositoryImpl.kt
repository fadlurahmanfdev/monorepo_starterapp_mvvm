package com.fadlurahmanf.monorepo.core_crypto.data.repositories

import com.fadlurahmanf.monorepo.core_crypto.data.enums.PaddingScheme
import com.fadlurahmanf.monorepo.core_crypto.others.CryptoException
import com.fadlurahmanf.monorepo.core_crypto.others.BaseCrypto
import java.lang.Exception
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CryptoAESRepositoryImpl : BaseCrypto(), CryptoAESRepository {
    override fun generateKey(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..32)
            .map { allowedChars.random() }
            .joinToString("")
    }

    override fun encryptCBC(plainText: String, secretKey: String, padding: PaddingScheme): String? {
        try {
            val key = SecretKeySpec(secretKey.toByteArray(), "AES")
            val iv = IvParameterSpec(ByteArray(16))
            val cipher = Cipher.getInstance("AES/CBC/${getPaddingScheme(padding)}")
            cipher.init(Cipher.ENCRYPT_MODE, key, iv)
            val encryptedBytes = when (padding) {
                PaddingScheme.NoPadding -> {
                    cipher.doFinal(padPlaintext(plainText).toByteArray())
                }

                PaddingScheme.PKCS5 -> {
                    cipher.doFinal(plainText.toByteArray())
                }

                PaddingScheme.PKCS7 -> {
                    cipher.doFinal(plainText.toByteArray())
                }

                else -> {
                    throw CryptoException(message = "NOT SUPPORTED PADDING")
                }
            }
            return encode(encryptedBytes)
        } catch (e: Exception) {
            return null
        }
    }

    override fun encryptECB(plainText: String, secretKey: String, padding: PaddingScheme): String? {
        try {
            val key = SecretKeySpec(secretKey.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/ECB/${getPaddingScheme(padding)}")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val encryptedBytes = when (padding) {
                PaddingScheme.NoPadding -> {
                    cipher.doFinal(padPlaintext(plainText).toByteArray())
                }

                PaddingScheme.PKCS5 -> {
                    cipher.doFinal(plainText.toByteArray())
                }

                PaddingScheme.PKCS7 -> {
                    cipher.doFinal(plainText.toByteArray())
                }

                else -> {
                    throw CryptoException(message = "NO SUPPORTED PADDING")
                }
            }
            return encode(encryptedBytes)
        } catch (e: Exception) {
            return null
        }
    }

    override fun decryptCBC(
        encryptedText: String,
        secretKey: String,
        padding: PaddingScheme
    ): String? {
        return try {
            val key = SecretKeySpec(secretKey.toByteArray(), "AES")
            val iv = IvParameterSpec(ByteArray(16))
            val cipher = Cipher.getInstance("AES/CBC/${getPaddingScheme(padding)}")
            cipher.init(Cipher.DECRYPT_MODE, key, iv)
            String(
                cipher.doFinal(
                    decode(encryptedText)
                )
            )
        } catch (e: Exception) {
            null
        }
    }

    override fun decryptECB(
        encryptedText: String,
        secretKey: String,
        padding: PaddingScheme
    ): String? {
        return try {
            val key = SecretKeySpec(secretKey.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/ECB/${getPaddingScheme(padding)}")
            cipher.init(Cipher.DECRYPT_MODE, key)
            String(
                cipher.doFinal(
                    decode(encryptedText)
                )
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun padPlaintext(plaintext: String): String {
        val blockSize = 16
        val paddingLength = blockSize - (plaintext.length % blockSize)
        val paddingChar = paddingLength.toChar()
        return plaintext + paddingChar.toString().repeat(paddingLength)
    }
}