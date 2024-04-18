package com.fadlurahmanf.monorepo.core_crypto

import com.github.fadlurahmanfdev.core_crypto.data.repositories.CryptoAESRepository
import com.github.fadlurahmanfdev.core_crypto.data.repositories.CryptoAESRepositoryImpl
import com.github.fadlurahmanfdev.core_crypto.data.repositories.CryptoED25519Repository
import com.github.fadlurahmanfdev.core_crypto.data.repositories.CryptoED25519RepositoryImpl
import com.github.fadlurahmanfdev.core_crypto.data.repositories.CryptoRSARepository
import com.github.fadlurahmanfdev.core_crypto.data.repositories.CryptoRSARepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class CryptoModule {

    @Provides
    fun provideCryptoRSARepository(): CryptoRSARepository {
        return CryptoRSARepositoryImpl()
    }

    @Provides
    fun provideCryptoAESRepository(): CryptoAESRepository {
        return CryptoAESRepositoryImpl()
    }

    @Provides
    fun provideCryptoED25519Repository(): CryptoED25519Repository {
        return CryptoED25519RepositoryImpl()
    }
}