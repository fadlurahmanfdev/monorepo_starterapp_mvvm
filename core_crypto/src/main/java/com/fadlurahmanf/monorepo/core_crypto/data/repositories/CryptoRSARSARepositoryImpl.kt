package com.fadlurahmanf.monorepo.core_crypto.data.repositories

import com.fadlurahmanf.monorepo.core_crypto.data.enums.PaddingScheme
import com.fadlurahmanf.monorepo.core_crypto.others.CryptoException
import com.fadlurahmanf.monorepo.core_crypto.data.model.CryptoKey
import com.fadlurahmanf.monorepo.core_crypto.others.BaseCrypto
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


class CryptoRSARSARepositoryImpl : BaseCrypto(), CryptoRSARepository {
    override fun generateKey(): CryptoKey {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(2048)
        val keyPair = keyGen.generateKeyPair()
        val publicKey = encode(keyPair.public.encoded)
        val privateKey = encode(keyPair.private.encoded)
        return CryptoKey(privateKey = privateKey, publicKey = publicKey)
    }

    override fun loadPublicKey(encodedPublicKey: String): PublicKey {
        val data = decode(encodedPublicKey.toByteArray())
        val spec = X509EncodedKeySpec(data)
        val fact: KeyFactory = KeyFactory.getInstance("RSA")
        return fact.generatePublic(spec)
    }

    override fun loadPrivateKey(encodedPrivateKey: String): PrivateKey {
        val data = decode(encodedPrivateKey.toByteArray())
        val spec = PKCS8EncodedKeySpec(data)
        val fact: KeyFactory = KeyFactory.getInstance("RSA")
        return fact.generatePrivate(spec)
    }

    override fun encrypt(
        plainText: String,
        encodedPublicKey: String,
        paddingScheme: PaddingScheme
    ): String? {
        return try {
            if (plainText.isEmpty()) {
                throw CryptoException(message = "TEXT CANNOT BE EMPTY")
            }

            if (encodedPublicKey.isEmpty()) {
                throw CryptoException(message = "KEY CANNOT BE EMPTY")
            }

            val cipher = Cipher.getInstance("RSA/ECB/${getPaddingScheme(paddingScheme)}")
            cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey(encodedPublicKey))
            val encryptedBytes = cipher.doFinal(plainText.toByteArray())
            return encode(encryptedBytes)
        } catch (e: CryptoException) {
            null
        } catch (e: Throwable) {
            null
        }
    }

    override fun decrypt(
        encrypted: String,
        encodedPrivateKey: String,
        paddingScheme: PaddingScheme
    ): String? {
        return try {
            if (encrypted.isEmpty()) {
                throw CryptoException(message = "TEXT CANNOT BE EMPTY")
            }

            if (encodedPrivateKey.isEmpty()) {
                throw CryptoException(message = "KEY CANNOT BE EMPTY")
            }

            val cipher = Cipher.getInstance("RSA/ECB/${getPaddingScheme(paddingScheme)}")
            cipher.init(Cipher.DECRYPT_MODE, loadPrivateKey(encodedPrivateKey))
            return String(cipher.doFinal(decode(encrypted)))
        } catch (e: CryptoException) {
            null
        } catch (e: Throwable) {
            null
        }
    }

    override fun createSignature(encodedPrivateKey: String, text:String): String {
        val instance = Signature.getInstance("MD5WithRSA")
        instance.initSign(loadPrivateKey(encodedPrivateKey))
        instance.update(text.toByteArray())
        return encode(instance.sign())
    }

    override fun verifySignature(encodedPublicKey: String, text: String, signature:String): Boolean {
        val instance = Signature.getInstance("MD5WithRSA")
        instance.initVerify(loadPublicKey(encodedPublicKey))
        instance.update(text.toByteArray())
        return instance.verify(decode(signature))
    }
}