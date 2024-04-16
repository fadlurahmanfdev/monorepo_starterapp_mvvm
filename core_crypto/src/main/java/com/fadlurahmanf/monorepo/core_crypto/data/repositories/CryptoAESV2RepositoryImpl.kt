package com.fadlurahmanf.monorepo.core_crypto.data.repositories

import android.util.Log
import com.fadlurahmanf.monorepo.core_shared.CoreSharedConstant
import com.fadlurahmanf.monorepo.core_crypto.data.enums.AESMethod
import com.fadlurahmanf.monorepo.core_crypto.others.BaseCryptoV2
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class CryptoAESV2RepositoryImpl @Inject constructor() : BaseCryptoV2(), CryptoAESV2Repository {
    override fun generateKey(): String {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        return encode(keyGen.generateKey().encoded)
    }

    override fun encrypt(encodedKey: String, plainText: String, method: AESMethod): String? {
        try {
            val cipher = Cipher.getInstance(getAESTransformationBasedOnFlow(method))
            val secretKey = SecretKeySpec(decode(encodedKey), "AES")
            val iv = IvParameterSpec(ByteArray(16))
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)
            return encode(cipher.doFinal(plainText.toByteArray()))
        } catch (e: Throwable) {
            Log.e(CoreSharedConstant.LOGGER_TAG, "failed encrypt: ${e.message}")
            return null
        }
    }

    override fun decrypt(encodedKey: String, encryptedText: String, method: AESMethod): String? {
        try {
            val cipher = Cipher.getInstance(getAESTransformationBasedOnFlow(method))
            val secretKey = SecretKeySpec(decode(encodedKey), "AES")
            val iv = IvParameterSpec(ByteArray(16))
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)
            return String(cipher.doFinal(decode(encryptedText)))
        } catch (e: Exception) {
            return null
        }
    }
}