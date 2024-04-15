package com.fadlurahmanf.monorepo.core_crypto.others.di

import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoAESRepository
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoAESRepositoryImpl
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoAESV2Repository
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoAESV2RepositoryImpl
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoED25519Repository
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoED25519RepositoryImpl
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoRSARepositoryImpl
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoRSARepository
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoRSAV2Repository
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoRSAV2RepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class CryptoModule {

    @Provides
    fun provideCryptoRSARepository(): CryptoRSARepository {
        return CryptoRSARepositoryImpl()
    }

    @Provides
    fun provideCryptoRSAV2Repository(): CryptoRSAV2Repository {
        return CryptoRSAV2RepositoryImpl()
    }

    @Provides
    fun provideCryptoAESRepository(): CryptoAESRepository {
        return CryptoAESRepositoryImpl()
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