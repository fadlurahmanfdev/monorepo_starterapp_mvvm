package com.fadlurahmanf.monorepo.core_crypto

import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoAESV2Repository
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoAESV2RepositoryImpl
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoED25519Repository
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoED25519RepositoryImpl
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoRSAV2Repository
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoRSAV2RepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class CryptoModule {

    @Provides
    fun provideCryptoRSAV2Repository(): CryptoRSAV2Repository {
        return CryptoRSAV2RepositoryImpl()
    }

    @Provides
    fun provideCryptoAESV2Repository(): CryptoAESV2Repository {
        return CryptoAESV2RepositoryImpl()
    }

    @Provides
    fun provideCryptoED25119Repository(): CryptoED25519Repository {
        return CryptoED25519RepositoryImpl()
    }
}