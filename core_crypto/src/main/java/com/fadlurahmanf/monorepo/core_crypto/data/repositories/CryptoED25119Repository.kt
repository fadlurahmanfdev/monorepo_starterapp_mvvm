package com.fadlurahmanf.monorepo.core_crypto.data.repositories

import com.fadlurahmanf.monorepo.core_crypto.data.model.CryptoKey

interface CryptoED25119Repository {
    fun generateKey(): CryptoKey

    fun generateSignature(plainText: String, encodedPrivateKey: String): String?

    fun verifySignature(text: String, signature: String, encodedPublicKey: String): Boolean
}