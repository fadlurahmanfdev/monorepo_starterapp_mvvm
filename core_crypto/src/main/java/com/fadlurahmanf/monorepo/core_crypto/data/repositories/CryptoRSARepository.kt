package com.fadlurahmanf.monorepo.core_crypto.data.repositories

import com.fadlurahmanf.monorepo.core_crypto.data.enums.PaddingScheme
import com.fadlurahmanf.monorepo.core_crypto.data.model.CryptoKey
import java.security.PrivateKey
import java.security.PublicKey

@Deprecated("use CryptoRSAV2Repository")
interface CryptoRSARepository {
    fun generateKey(): CryptoKey
    fun loadPublicKey(encodedPublicKey: String): PublicKey
    fun loadPrivateKey(encodedPrivateKey: String): PrivateKey
    fun encrypt(
        plainText: String,
        encodedPublicKey: String,
        paddingScheme: PaddingScheme = PaddingScheme.PKCS1
    ): String?

    fun decrypt(
        encrypted: String,
        encodedPrivateKey: String,
        paddingScheme: PaddingScheme = PaddingScheme.PKCS1
    ): String?

    fun createSignature(encodedPrivateKey: String, text: String): String
    fun verifySignature(encodedPublicKey: String, text: String, signature: String): Boolean
}