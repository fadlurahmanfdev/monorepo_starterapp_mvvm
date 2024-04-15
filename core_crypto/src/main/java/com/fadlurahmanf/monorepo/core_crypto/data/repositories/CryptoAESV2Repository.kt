package com.fadlurahmanf.monorepo.core_crypto.data.repositories

import com.fadlurahmanf.monorepo.core_crypto.data.enums.AESMethod

interface CryptoAESV2Repository {
    fun generateKey(): String
    fun encrypt(encodedKey: String, plainText: String, method: AESMethod): String?
    fun decrypt(encodedKey: String, encryptedText: String, method: AESMethod): String?
}