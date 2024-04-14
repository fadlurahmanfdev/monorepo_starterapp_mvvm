package com.fadlurahmanf.monorepo.core_crypto.others.di

import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoAESRepository
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoAESRepositoryImpl
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoED25119Repository
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoED25119RepositoryImpl
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoRSARSARepositoryImpl
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoRSARepository
import dagger.Module
import dagger.Provides

@Module
class CryptoModule {

    @Provides
    fun provideCryptoRSARepository(): CryptoRSARepository {
        return CryptoRSARSARepositoryImpl()
    }

    @Provides
    fun provideCryptoAESRepository(): CryptoAESRepository {
        return CryptoAESRepositoryImpl()
    }

    @Provides
    fun provideCryptoED25119Repository(): CryptoED25119Repository {
        return CryptoED25119RepositoryImpl()
    }
}